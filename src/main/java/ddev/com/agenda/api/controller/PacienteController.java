package ddev.com.agenda.api.controller;

import ddev.com.agenda.api.mapper.PacienteMapper;
import ddev.com.agenda.api.request.PacienteRequest;
import ddev.com.agenda.api.response.PacienteResponse;
import ddev.com.agenda.domain.entity.Paciente;
import ddev.com.agenda.domain.service.PacienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Api(value = "API REST PACIENTES")
@CrossOrigin(origins = "*")
public class PacienteController {

    private final PacienteService service;
    private final PacienteMapper mapper;

    @ApiOperation(value = "Salva Paciente")
    @PostMapping("/paciente")
    public ResponseEntity<PacienteResponse> salvar (@Valid @RequestBody PacienteRequest request){
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.salvar(paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteResponse);
    }

    @ApiOperation(value = "Lista Paciente")
    @GetMapping("/pacientes")
    public ResponseEntity<List<PacienteResponse>> listarTodos(){
        List<Paciente> pacientes = service.listarTodos();
        List<PacienteResponse> pacienteResponse = mapper.toPacienteResponseList(pacientes);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);

    }
    @ApiOperation(value = "Busca Paciente Por Id")
    @GetMapping("/paciente/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id){
        Optional<Paciente> optPaciente = service.buscarPorId(id);

            if (optPaciente.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toPacienteResponse(optPaciente.get()));
}
    @ApiOperation(value = "Edita Paciente")
    @PutMapping("/paciente/{id}")
    public ResponseEntity<PacienteResponse> alterar(@Valid @PathVariable Long id,  @RequestBody PacienteRequest request) {
        Paciente paciente = mapper.toPaciente(request);
        Paciente pacienteSalvo = service.alterar(id,paciente);
        PacienteResponse pacienteResponse = mapper.toPacienteResponse(pacienteSalvo);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteResponse);
    }

    @ApiOperation(value = "Deleta Paciente")
    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
