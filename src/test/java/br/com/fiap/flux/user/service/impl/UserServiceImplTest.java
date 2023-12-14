package br.com.fiap.flux.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;
    
    @Test
    void testCreate() {
        User user = User.builder().name("TESTE").build();

        Mono<User> expected = Mono.just(user);

        doReturn(expected).when(repository).save(user);

        var actual = userService.create(user);

        assertEquals(expected.block(), actual.block());
    }

    @Test
    void testDelete() {
        var id = UUID.randomUUID().toString();
        doReturn(Mono.empty()).when(repository).deleteById(id);
        userService.delete(id);
        verify(repository).deleteById(id);
    }
    
    @Test
    void testFind() {
        var id = UUID.randomUUID().toString();
        User user = User.builder().name("found!").build();
        var expected = Mono.just(user);
        doReturn(expected).when(repository).findById(id);
        var actual = userService.find(id);

        assertEquals(expected.block(), actual.block());
    }

    @Test
    void testFindAll() {
        User one = User.builder().name("one").build();
        User two = User.builder().name("two").build();
        User three = User.builder().name("three").build();

        var list = Flux.fromIterable(List.of(one, two, three));

        doReturn(list).when(repository).findAll();

        assertEquals(list, userService.findAll(Pageable.ofSize(1)));
    }

    @Test
    void testUpdate() {
        var id = UUID.randomUUID().toString();
        User outdated = User.builder().name("outdated").build();
        User toUpdate = User.builder().email("updated@email.com").build();
        User updated = outdated.update(toUpdate);

        doReturn(Mono.just(outdated)).when(repository).findById(id);
        doReturn(Mono.just(updated)).when(repository).save(updated);

        var actual = userService.update(id, toUpdate);

        assertEquals(outdated.getName(), actual.block().getName());
        assertEquals(toUpdate.getEmail(), actual.block().getEmail());
    }
}
