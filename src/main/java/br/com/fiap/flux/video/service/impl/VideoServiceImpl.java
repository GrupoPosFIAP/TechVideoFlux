package br.com.fiap.flux.video.service.impl;

import br.com.fiap.flux.enums.Category;
import br.com.fiap.flux.exception.EntityNotFoundException;
import br.com.fiap.flux.user.repository.UserRepository;
import br.com.fiap.flux.utils.CriteriaBuilder;
import br.com.fiap.flux.video.domain.Video;
import br.com.fiap.flux.video.domain.VideoCriteria;
import br.com.fiap.flux.video.repository.VideoRepository;
import br.com.fiap.flux.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final ReactiveMongoTemplate mongoTemplate;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<PageImpl<Video>> findAll(Pageable pageable, VideoCriteria videoCriteria) {
        Query query = new Query()
                .addCriteria(CriteriaBuilder.buildCriteria(videoCriteria))
                .with(pageable);

        Flux<Video> videos = mongoTemplate.find(query, Video.class);

        return videos.collectList()
                .zipWith(this.videoRepository.count())
                .map(video -> new PageImpl<>(video.getT1(), pageable, video.getT2()));
    }

    @Override
    public Mono<Video> findById(UUID id) {
        return this.videoRepository.findById(id).switchIfEmpty(Mono.error(new EntityNotFoundException()));
    }

    @Override
    public Mono<Video> insert(Video video) {
        return this.videoRepository.save(video).switchIfEmpty(Mono.error(new EntityNotFoundException("Erro ao salvar o vídeo")));
    }

    @Override
    public Mono<Void> update(UUID id, Video video) {
        return findById(id).flatMap(foundedVideo -> {
            foundedVideo.setTitulo(video.getTitulo());
            foundedVideo.setDescricao(video.getDescricao());
            foundedVideo.setUrl(video.getUrl());
            foundedVideo.setDataPublicacao(video.getDataPublicacao());
            return this.videoRepository.save(foundedVideo);
        }).then();
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return this.videoRepository.deleteById(id).then();
    }

    @Override
    public Flux<Video> recommendations(String userId) {

        var user = userRepository
                .findById(userId)
                .switchIfEmpty(Mono.error(EntityNotFoundException::new));

        Flux<Category> categories = user
                .map(u -> {
                    var favs = u.getFavorites();
                    return favs
                            .stream()
                            .flatMap(vid -> vid.getCategorias().stream())
                            .distinct()
                            .collect(toList());
                })
                .flatMapMany(Flux::fromIterable);

        return ((VideoRepository) videoRepository).findByCategoriasIn(categories);
    }

    @Override
    public Mono<Void> favoriteVideo(String userId, UUID videoId) {
        var user = userRepository
                .findById(userId)
                .switchIfEmpty(Mono.error(EntityNotFoundException::new));

        var video = findById(videoId);

        user = user.map(u -> {
            u.getFavorites().add(video.block());
            return u;
        });

        user.subscribe(userRepository::save);

        return Mono.empty();
    }
}
