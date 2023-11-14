package br.com.fiap.flux.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericMessage {

    private String errorCode;

    private String errorMessage;

}
