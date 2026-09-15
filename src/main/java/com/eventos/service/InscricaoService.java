package com.eventos.service;

import com.eventos.exception.EventoLotadoException;
import com.eventos.exception.RegistroDuplicadoException;
import com.eventos.model.Evento;
import com.eventos.model.Inscricao;
import com.eventos.model.Participante;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.InscricaoRepository;
import com.eventos.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Transactional
    public Inscricao inscreverParticipante(Long eventoId, Long participanteId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado"));

        Participante participante = participanteRepository.findById(participanteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));

        if (inscricaoRepository.existsByEventoIdAndParticipanteId(eventoId, participanteId)) {
            throw new RegistroDuplicadoException("O participante já está inscrito neste evento");
        }

        long totalInscritos = inscricaoRepository.countByEventoId(eventoId);

        if (evento.getCapacidadeMaxima() != null && totalInscritos >= evento.getCapacidadeMaxima()) {
            throw new EventoLotadoException("O evento está lotado");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);
        inscricao.setDataInscricao(LocalDate.now());

        return inscricaoRepository.save(inscricao);
    }

    public List<Participante> listarParticipantesPorEvento(Long eventoId) {
        return inscricaoRepository.findParticipantesByEventoId(eventoId);
    }

    @Transactional
    public void cancelarInscricao(Long inscricaoId) {
        if (!inscricaoRepository.existsById(inscricaoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscrição não encontrada");
        }
        inscricaoRepository.deleteById(inscricaoId);
    }
}