package br.com.fiap.flux.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.service.UserService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    
    @PostMapping
    public Mono<User> create(@RequestBody User entity) {
        return service.create(entity);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return service.delete(id).then();
    }

    @GetMapping("/{id}")
    public Mono<User> find(@PathVariable("id") String id) {
        return service.find(id);
    }

    @GetMapping
    public Mono<Page<User>> findAll(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "DESC") String direction) {
        
        var pageable = PageRequest.of(page, size, Direction.fromString(direction), "name");
        return service.findAll(pageable);
    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") String id, @RequestBody User toUpdate) {
        return service.update(id, toUpdate);
    }
}
