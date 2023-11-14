package br.com.fiap.flux.video.repository;

import br.com.fiap.flux.enums.Category;
import br.com.fiap.flux.video.domain.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, UUID>,
        ReactiveQueryByExampleExecutor<Video> {

    Flux<Video> findByCategoriesIn(Flux<Category> categories);
}
