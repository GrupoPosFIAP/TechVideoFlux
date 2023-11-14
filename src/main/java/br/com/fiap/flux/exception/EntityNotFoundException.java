package br.com.fiap.flux.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class EntityNotFoundException extends HttpClientErrorException {
    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Entidade não encontrada");
    }

    public EntityNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public EntityNotFoundException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
