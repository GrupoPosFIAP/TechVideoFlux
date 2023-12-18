package br.com.fiap.flux.user.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.fiap.flux.exception.EntityNotFoundException;
import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.mapper.UserMapper;
import br.com.fiap.flux.user.repository.UserRepository;
import br.com.fiap.flux.user.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper userMapper;

    @Override
    public Mono<User> create(User user) {
        return repository.save(user).switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }

    @Override
    public Mono<User> update(String id, User toUpdate) {
        return repository
                .findById(id)
                .flatMap(outdated -> {
                    userMapper.updatePartial(outdated, toUpdate);
                    return repository.save(outdated);
                })
                .switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id).then();
    }

    @Override
    public Mono<Page<User>> findAll(Pageable pageable) {
        return repository
            .findByIdNotNull(pageable)
            .collectList()
            .zipWith(repository.count())
            .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    public Mono<User> find(String id) {
        return repository
            .findById(id)
            .switchIfEmpty(Mono.error(EntityNotFoundException::new));
    }
}
