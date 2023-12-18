package br.com.fiap.flux.user.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.fiap.flux.controller.BaseControllerUnitTests;
import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.service.UserService;
import io.restassured.RestAssured;
import reactor.core.publisher.Mono;

public class UserControllerTest extends BaseControllerUnitTests {

    @MockBean
    private UserService userService;

    @Test
    void testCreate() {
        var user = Mono.just(User.builder().name("John").email("johndoe@tests.com").build());
        doReturn(user).when(userService).create(any());

        var response = RestAssured
                .given()
                .contentType("application/json")
                .body(
                        """
                                {
                                    "name": "John",
                                    "email": "johndoe@tests.com"
                                }
                                """)
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(User.class);

        assertThat(response.getName()).isEqualTo("John");
        assertThat(response.getEmail()).isEqualTo("johndoe@tests.com");

    }

    @Test
    void testDelete() {
        doReturn(Mono.empty()).when(userService).delete(anyString());

        var id = "1";
        RestAssured
                .when()
                .delete("/users/{id}", id)
                .then()
                .statusCode(200);

        verify(userService).delete(id);
    }

    @Test
    void testFind() {
        var user = Mono.just(User.builder().name("John").email("johndoe@tests.com").build());
        doReturn(user).when(userService).find(any());

        var id = "1";
        RestAssured
                .when()
                .get("/users/{id}", id)
                .then()
                .statusCode(200)
                .body("name", equalTo("John"))
                .body("email", equalTo("johndoe@tests.com"));

        verify(userService).find(id);
    }

    @Test
    void testFindAll() {
        var user = User.builder().name("John").email("johndoe@tests.com").build();

        var page = new PageImpl<User>(List.of(user));

        var expected = Mono.just(page);
        doReturn(expected).when(userService).findAll(any(Pageable.class));

        RestAssured
                .given()
                .contentType("application/json")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("content[0].name", equalTo(user.getName()))
                .body("content[0].email", equalTo(user.getEmail()));
    }

    @Test
    void testUpdate() {
        var name = "John";
        var email = "john@doe.com";

        doReturn(Mono.just(User.builder().name(name).email(email).build())).when(userService).update(any(), any());

        RestAssured
                .given()
                .contentType("application/json")
                .body(
                        """
                                {
                                    "name": "%s",
                                    "email": "%s"
                                }
                                """.formatted(name, email))
                .when()
                .put("/users/1")
                .then()
                .statusCode(200)
                .body("name", equalTo(name))
                .body("email", equalTo(email));
    }
}
