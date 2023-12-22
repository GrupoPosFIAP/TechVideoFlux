package br.com.fiap.flux.builder;

import br.com.fiap.flux.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class UserBuilder {

    public static User cadastrarUser() {

        User user = new User();

        user.setId(UUID.randomUUID().toString());
        //user.setId(String.valueOf(userId));
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedBy("Cesar");
        user.setName("Arquitetura de Software");
        user.setFavorites(new ArrayList<>());

        return user;
    }
}
