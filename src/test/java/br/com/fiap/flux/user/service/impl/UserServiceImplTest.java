package br.com.fiap.flux.user.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.mapper.UserMapper;
import br.com.fiap.flux.user.mapper.UserMapperImpl;
import br.com.fiap.flux.user.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository repository;

    @BeforeEach
    private void initTests() {
        UserMapper mapper = new UserMapperImpl();
        userService = new UserServiceImpl(repository, mapper);
    }
    
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

        var page = new PageImpl<User>(List.of(one, two, three));

        var expected = Mono.just(page);

        doReturn(list).when(repository).findByIdNotNull(any(Pageable.class));
        doReturn(Mono.just(3l)).when(repository).count();

        assertEquals(expected.block().getContent(), userService.findAll(PageRequest.of(0, 10, Sort.by("name"))).block().getContent());
    }

    @Test
    void testUpdate() {
        var id = UUID.randomUUID().toString();
        User outdated = User.builder().name("outdated").build();
        User toUpdate = User.builder().email("updated@email.com").build();
        User updated = User.builder().name(outdated.getName()).email(toUpdate.getEmail()).build();

        doReturn(Mono.just(outdated)).when(repository).findById(id);
        doReturn(Mono.just(updated)).when(repository).save(updated);

        var actual = userService.update(id, toUpdate);

        assertEquals(outdated.getName(), actual.block().getName());
        assertEquals(toUpdate.getEmail(), actual.block().getEmail());
    }
}
