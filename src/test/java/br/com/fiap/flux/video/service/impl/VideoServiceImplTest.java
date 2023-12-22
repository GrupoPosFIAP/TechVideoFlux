package br.com.fiap.flux.video.service.impl;

import br.com.fiap.flux.builder.UserBuilder;
import br.com.fiap.flux.builder.VideoBuilder;
import br.com.fiap.flux.exception.EntityNotFoundException;
import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.repository.UserRepository;
import br.com.fiap.flux.video.domain.Estatistica;
import br.com.fiap.flux.video.domain.Video;
import br.com.fiap.flux.video.domain.VideoCriteria;
import br.com.fiap.flux.video.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VideoServiceImplTest {

    @InjectMocks
    private VideoServiceImpl videoService;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReactiveMongoTemplate mongoTemplate;

    private Video videoMockado = VideoBuilder.publicarVideo();
    private User userMockado = UserBuilder.cadastrarUser();

    @Test
    public void testFindAllVideos() {

        when(mongoTemplate.find(any(), any())).thenReturn(Flux.just(List.of(videoMockado, videoMockado)));
        when(videoRepository.count()).thenReturn(Mono.just(1L));

        Mono<PageImpl<Video>> videos = this.videoService.findAll(Pageable.ofSize(25), new VideoCriteria());

        PageImpl<Video> pageVideo = new PageImpl<>(videos.block().getContent(), videos.block().getPageable(), videos.block().getTotalElements());

        StepVerifier.create(videos)
                .expectNext(pageVideo)
                .verifyComplete();
    }

    @Test
    public void testFindVideoById() {
        UUID videoId = UUID.randomUUID();
        when(this.videoRepository.findById(videoId)).thenReturn(Mono.just(videoMockado));
        StepVerifier.create(videoService.findById(videoId)).expectNext(videoMockado).verifyComplete();
    }

    @Test
    public void expectErrorWhenNotFindVideo() {
        UUID videoId = UUID.randomUUID();
        when(this.videoRepository.findById(videoId)).thenReturn(Mono.empty());
        StepVerifier.create(videoService.findById(videoId)).expectError(EntityNotFoundException.class).verify();
    }

    @Test
    public void testSaveVideo() {
        when(this.videoRepository.save(videoMockado)).thenReturn(Mono.just(videoMockado));
        StepVerifier.create(videoService.insert(videoMockado)).expectNext(videoMockado).verifyComplete();
    }

    @Test
    public void expectErrorWhenSaveNullVideo() {
        when(this.videoRepository.save(videoMockado)).thenReturn(Mono.empty());
        StepVerifier.create(videoService.insert(videoMockado)).expectError(EntityNotFoundException.class).verify();
    }

    @Test
    public void testUpdateVideo() {
        UUID videoId = UUID.randomUUID();
        when(this.videoRepository.findById(videoId)).thenReturn(Mono.just(videoMockado));
        when(this.videoRepository.save(videoMockado)).thenReturn(Mono.just(videoMockado));

        StepVerifier.create(this.videoService.update(videoId, videoMockado)).expectComplete().verify();
    }

    @Test
    public void testDeleteVideo() {
        UUID videoId = UUID.randomUUID();
        when(this.videoRepository.deleteById(videoId)).thenReturn(Mono.empty());
        StepVerifier.create(this.videoService.delete(videoId)).expectComplete().verify();
    }

    @Test
    public void testFavoriteVideo() {

        // Criar IDs de exemplo
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();

        // Mock do UserRepository e VideoRepository
        when(userRepository.findById(String.valueOf(userId))).thenReturn(Mono.just(userMockado));
        when(videoRepository.findById(videoId)).thenReturn(Mono.just(videoMockado));
        when(videoRepository.save(any(Video.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Chamar o método e verificar o resultado
        Mono<Void> result = videoService.favoriteVideo(userId.toString(), videoId);

        // Verificar se o vídeo foi adicionado à lista de favoritos do usuário
        assertEquals(1, userMockado.getFavorites().size());
        assertTrue(userMockado.getFavorites().contains(videoMockado));
        assertEquals(Long.valueOf(3), videoMockado.getContadorFavoritos());
    }

    @Test
    public void testDefavoriteVideo() {

        // Criar IDs de exemplo
        UUID userId = UUID.randomUUID();
        UUID videoId = UUID.randomUUID();

        // Mock do UserRepository
        when(userRepository.findById(String.valueOf(userId))).thenReturn(Mono.just(userMockado));
        when(videoRepository.findById(videoId)).thenReturn(Mono.just(videoMockado));
        when(videoRepository.save(any(Video.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Chamar o método e verificar o resultado
        Mono<Void> result = videoService.defavoriteVideo(userId.toString(), videoId);

        // Verificar se o vídeo foi removido da lista de favoritos do usuário
        assertEquals(1, userMockado.getFavorites().size());
        assertEquals(Long.valueOf(1), videoMockado.getContadorFavoritos());
    }

    @Test
    public void testEstatisticas() {

        // Mock do VideoRepository
        when(videoRepository.findAll()).thenReturn(Flux.empty());

        // Chamar o método
        Mono<Estatistica> result = videoService.estatisticas();

        // Verificar o resultado
        StepVerifier.create(result)
                .expectNextMatches(estatistica -> estatistica.getQuantidadeVideos() == 0
                        && estatistica.getQuantidadeVideosFavoritos() == 0
                        && estatistica.getMediaVisualizacoes() == 0)
                .verifyComplete();
    }
}
