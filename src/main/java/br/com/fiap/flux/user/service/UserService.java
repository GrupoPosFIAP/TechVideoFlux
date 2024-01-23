package br.com.fiap.flux.user.service;

import br.com.fiap.flux.video.domain.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fiap.flux.user.domain.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> create(User user);

    Mono<User> update(String id, User user);

    public Mono<Void> delete(String id);

    public Mono<Page<User>> findAll(Pageable pageable);

    public Mono<User> find(String id);

}
