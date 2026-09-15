package com.eventos.controller;

import com.eventos.dto.ParticipanteDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.service.ParticipanteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos/api/participante")
@RequiredArgsConstructor
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> cadastrar(@Valid @RequestBody ParticipanteDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(participanteService.cadastrar(dto));
    }

    @GetMapping
    @Operation(summary = "Lista os participantes")
    public ResponseEntity<List<ParticipanteResponseDTO>> listar(){
        return ResponseEntity.ok(participanteService.listar());
    }
}
