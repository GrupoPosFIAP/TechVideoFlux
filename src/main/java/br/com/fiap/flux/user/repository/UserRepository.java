package br.com.fiap.flux.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.flux.user.domain.User;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String>{

    Flux<User> findByIdNotNull(Pageable pageable);
}
