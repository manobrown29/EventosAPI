package com.eventos.exception;

import lombok.Getter;

@Getter
public class RegistroDuplicadoException extends RuntimeException {

    private final String campo;

    public RegistroDuplicadoException(String message, String campo) {
        super(message);
        this.campo = campo;
    }
}
