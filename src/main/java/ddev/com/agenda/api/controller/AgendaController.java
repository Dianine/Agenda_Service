package ddev.com.agenda.api.controller;

import ddev.com.agenda.api.mapper.AgendaMapper;
import ddev.com.agenda.api.request.AgendaResquest;
import ddev.com.agenda.api.response.AgendaResponse;
import ddev.com.agenda.domain.entity.Agenda;
import ddev.com.agenda.domain.service.AgendaService;
import ddev.com.agenda.exception.BusinessException;
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
@RequestMapping("/agenda")
//@Api(value = "API REST AGENDA")
//@CrossOrigin(origins = "*")
public class AgendaController {

    private final AgendaService service;
    private final AgendaMapper mapper;

    @PostMapping
    //@ApiOperation(value = "Salva Agenda")
    public ResponseEntity<AgendaResponse> salvar (@Valid @RequestBody AgendaResquest resquest){
        Agenda agenda = mapper.toAgenda(resquest);
        Agenda agendaSalva = service.salvar(agenda);
        AgendaResponse agendaResponse = mapper.toAgendaResponse(agendaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaResponse);
    }

    @GetMapping
    //@ApiOperation(value = "Lista Agendas")
    public ResponseEntity<List<AgendaResponse>> buscarTodos(){
        List<Agenda> agendas = service.listarTodos();
        List<AgendaResponse> agendaResponses = mapper.toAgendaResponseList(agendas);

        return ResponseEntity.status(HttpStatus.OK).body(agendaResponses);
    }

    @GetMapping("/{id}")
    //@ApiOperation(value = "Lista Agenda Por Id")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Long id){
        Optional<Agenda> optAgenda = service.buscarPorId(id);
        if (optAgenda.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        AgendaResponse agendaResponse = mapper.toAgendaResponse(optAgenda.get());
        return ResponseEntity.status(HttpStatus.OK).body(agendaResponse);

    }

}
