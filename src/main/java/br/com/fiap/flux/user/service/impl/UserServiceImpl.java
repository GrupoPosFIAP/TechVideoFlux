package br.com.fiap.flux.user.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.fiap.flux.exception.EntityNotFoundException;
import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.repository.UserRepository;
import br.com.fiap.flux.user.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public Mono<User> create(User user) {
        return repository.save(user).switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }

    @Override
    public Mono<User> update(String id, User toUpdate) {
        return repository
                .findById(id)
                .flatMap(outdated -> {
                    var updated = outdated.update(toUpdate);
                    return repository.save(updated);
                })
                .switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id).then();
    }

    @Override
    public Flux<User> findAll(Pageable pageable) {
        return repository.findAll();
    }

    @Override
    public Mono<User> find(String id) {
        return repository
            .findById(id)
            .switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }
}
