package com.eventos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoDTO> handleValidacao(MethodArgumentNotValidException ex) {
        List<ErroCampoDTO> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErroCampoDTO(fe.getField(), fe.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroValidacaoDTO(400, erros));
    }

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<ErroDTO> handleNaoEncontrado(EmailDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroDTO(404, ex.getMessage()));
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErroDTO> handleNaoEncontrado(RegistroDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErroDTO(409, ex.getMessage()));
    }
}

