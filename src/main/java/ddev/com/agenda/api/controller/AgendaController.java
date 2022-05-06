package ddev.com.agenda.api.controller;

import ddev.com.agenda.api.mapper.AgendaMapper;
import ddev.com.agenda.api.request.AgendaRequest;
import ddev.com.agenda.api.response.AgendaResponse;
import ddev.com.agenda.domain.entity.Agenda;
import ddev.com.agenda.domain.service.AgendaService;
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
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@Api(value = "API REST AGENDA")
@CrossOrigin(origins = "*")
public class AgendaController {

    private final AgendaService service;
    private final AgendaMapper mapper;

    @ApiOperation(value = "SALVA AGENDA")
    @PostMapping("/agenda")
    public ResponseEntity<AgendaResponse> salvar (@Valid @RequestBody AgendaRequest request){
        Agenda agenda = mapper.toAgenda(request);
        Agenda agendaSalva = service.salvar(agenda);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(agendaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
    }

    @ApiOperation(value = "LISTA TODAS AS AGENDAS")
    @GetMapping("/agendas")
    public ResponseEntity<List<AgendaResponse>> buscarTodos(){
        List<Agenda> agendas = service.listarTodos();
        List<AgendaResponse> agendaResponses = mapper.toAgendaResponseList(agendas);

        return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
    }

    @ApiOperation(value = "LISTA AGENDA POR ID")
    @GetMapping("/agenda/{id}")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id){
        Optional<Agenda> optAgenda = service.buscarPorId(id);
        if (optAgenda.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

}
