package br.com.fiap.flux.user.service.impl;

import br.com.fiap.flux.exception.EntityNotFoundException;
import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.repository.UserRepository;
import br.com.fiap.flux.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Mono<User> create(User user) {
        return repository.save(user).switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }
}
