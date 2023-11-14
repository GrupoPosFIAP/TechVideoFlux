package br.com.fiap.flux.video.service;

import br.com.fiap.flux.video.domain.Video;
import br.com.fiap.flux.video.domain.VideoCriteria;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VideoService {

    Mono<PageImpl<Video>> findAll(Pageable pageable, VideoCriteria videoCriteria);

    Mono<Video> findById(UUID id);

    Mono<Video> insert(Video video);

    Mono<Void> update(UUID id, Video video);

    Mono<Void> delete(UUID id);

    Mono<Void> favoriteVideo(String userId, UUID videoId);

    Flux<Video> recommendations(String userId);
}
