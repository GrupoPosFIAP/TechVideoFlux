package br.com.fiap.flux.user.service;

import br.com.fiap.flux.user.domain.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> create(User user);

}
