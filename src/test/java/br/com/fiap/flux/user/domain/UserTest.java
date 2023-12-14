package br.com.fiap.flux.user.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class UserTest {
    
    @Test
    void testUpdate() {
        var now = LocalDateTime.now();
        User user1 = User
            .builder()
            .createdDate(now)
            .build();

        User user2 = User
            .builder()
            .name("John")
            .email("John@johndoe.com")
            .build();

        var updated = user1.update(user2);

        assertEquals(updated.getName(), user2.getName());
        assertEquals(updated.getEmail(), user2.getEmail());
        assertEquals(updated.getCreatedDate(), user1.getCreatedDate());
    }
}
