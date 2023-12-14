package br.com.fiap.flux.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import br.com.fiap.flux.user.domain.User;
import br.com.fiap.flux.user.service.UserService;
import io.restassured.RestAssured;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    void testCreate() {
        var user = Mono.just(User.builder().name("Teste").build());
        doReturn(user).when(userService).create(any());

        RestAssured
            .given()
            .auth()
            .none()
            .contentType("application/json")
            .body(
                """
                {
                    "name": "John",
                    "email": "johndoe@tests.com"
                }
                """
                )
            .when()
            .post("/users")
            .then()
            .statusCode(200);

    }

    @Test
    void testDelete() {

    }

    @Test
    void testFind() {
        
    }

    @Test
    void testFindAll() {

    }

    @Test
    void testUpdate() {

    }
}
