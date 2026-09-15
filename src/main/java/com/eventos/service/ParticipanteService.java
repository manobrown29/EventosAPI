package com.eventos.service;

import com.eventos.dto.ParticipanteDTO;
import com.eventos.dto.ParticipanteResponseDTO;
import com.eventos.model.Participante;
import com.eventos.repository.ParticipanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    public ParticipanteResponseDTO cadastrar(ParticipanteDTO dto){

        Participante participante = Participante.builder()
                .nome(dto.nome())
                .email(dto.email())
                .build();

        participante = participanteRepository.save(participante);
        return toResponseDTO(participante);
    }

    public List <ParticipanteResponseDTO> listar(){
        return participanteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private ParticipanteResponseDTO toResponseDTO(Participante participante) {
        return new ParticipanteResponseDTO(
                participante.getId(),
                participante.getNome(),
                participante.getEmail()
        );
    }
}
