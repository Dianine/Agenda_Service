package ddev.com.agenda.api.controller;

import ddev.com.agenda.api.mapper.PacienteMapper;
import ddev.com.agenda.api.request.PacienteRequest;
import ddev.com.agenda.api.response.PacienteResponse;
import ddev.com.agenda.domain.entity.Paciente;
import ddev.com.agenda.domain.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper mapper;

    @PostMapping
    public ResponseEntity<PacienteResponse> salvar (@RequestBody PacienteRequest request){
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.salvar(paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listarTodos(){
        List<Paciente> pacientes = service.listarTodos();
        List<PacienteResponse> pacienteResponse = mapper.toPacienteResponseList(pacientes);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);

    }
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id){
        Optional<Paciente> optPaciente = service.buscarPorId(id);
            if (optPaciente.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toPacienteResponse(optPaciente.get()));
}
    @PutMapping
    public ResponseEntity<PacienteResponse> alterar(@RequestBody PacienteRequest request) {
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.salvar(paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
