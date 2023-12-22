package br.com.fiap.flux.video.service.impl;

import br.com.fiap.flux.builder.UserBuilder;
import br.com.fiap.flux.builder.VideoBuilder;
import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.repository.UserRepository;
import br.com.fiap.flux.video.domain.Estatistica;
import br.com.fiap.flux.video.domain.Video;
import br.com.fiap.flux.video.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private Video videoMockado = VideoBuilder.publicarVideo();
    private User userMockado = UserBuilder.cadastrarUser();

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
