package ddev.com.agenda.domain.service;

import ddev.com.agenda.domain.entity.Paciente;
import ddev.com.agenda.domain.repository.PacienteRepository;
import ddev.com.agenda.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;


    public Paciente salvar(Paciente paciente){
        boolean existeCpf = false;
        Optional<Paciente> optPaciente = repository.findByCpf(paciente.getCpf());
            if(optPaciente.isPresent()){
                if(!optPaciente.get().getId().equals(paciente.getId())){
                    existeCpf = true;
                }
            }
            if(existeCpf){
                throw new BusinessException("CPF já cadastrado");
            }
        return repository.save(paciente);

    }
    public List<Paciente> listarTodos(){
        return repository.findAll();

    }
    public Optional<Paciente> buscarPorId(Long id){
        return repository.findById(id);

    }
    public void deletar (long id){
        repository.deleteById(id);
    }



}
