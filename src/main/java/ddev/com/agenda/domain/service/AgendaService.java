package ddev.com.agenda.domain.service;

import ddev.com.agenda.domain.entity.Agenda;
import ddev.com.agenda.domain.entity.Paciente;
import ddev.com.agenda.domain.repository.AgendaRepository;
import ddev.com.agenda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AgendaService {

    private final AgendaRepository repository;
    private final PacienteService pacienteService;

    public Agenda salvar(Agenda agenda) {
        //1. verificar se paciente existe
        Optional<Paciente> optPaciente = pacienteService.buscarPorId(agenda.getPaciente().getId());
        if (optPaciente.isEmpty()) {
            throw new BusinessException("Paciente não encontrado");
        }
        //2. validar o horário
        Optional<Agenda> optHorario = repository.findByHorario(agenda.getHorario());
        if (optHorario.isPresent()) {
            throw new BusinessException("Horário já reservado, escolha outro.");
        }
        agenda.setPaciente(optPaciente.get());
        agenda.setDataCriacao(LocalDateTime.now());

        return repository.save(agenda);
    }

    public List<Agenda> listarTodos() {
        return repository.findAll();
    }

    public Optional<Agenda> buscarPorId(Long id) {
        return repository.findById(id);
    }

}
