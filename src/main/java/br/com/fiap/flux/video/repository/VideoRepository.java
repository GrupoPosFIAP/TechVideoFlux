package br.com.fiap.flux.video.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.flux.enums.Category;
import br.com.fiap.flux.video.domain.Video;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, UUID> {

    Flux<Video> findByCategoriasIn(Flux<Category> categories);

    Mono<Long> countByContadorFavoritosGreaterThan(Long quantity);

    Mono<Long> countAllByContadorVisualizacoes();
}
