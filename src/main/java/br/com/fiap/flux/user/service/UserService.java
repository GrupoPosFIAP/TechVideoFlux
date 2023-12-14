package br.com.fiap.flux.user.service;

import org.springframework.data.domain.Pageable;

import br.com.fiap.flux.user.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> create(User user);

    Mono<User> update(String id, User user);

    public Mono<Void> delete(String id);

    public Flux<User> findAll(Pageable pageable);

    public Mono<User> find(String id);

}
