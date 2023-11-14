package br.com.fiap.flux.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericMessage> handlerException(Exception ex) {
        log.debug(String.valueOf(ex));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericMessage(HttpStatus.INTERNAL_SERVER_ERROR.name(), "Ocorreu um erro durante o processamento da solicitação."));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GenericMessage> handlerEntityNotFoundException(EntityNotFoundException ex) {
        log.debug(String.valueOf(ex));
        int statusCode = ex.getStatusCode().value();
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new GenericMessage(HttpStatus.valueOf(statusCode).name(), ex.getStatusText()));
    }

}
