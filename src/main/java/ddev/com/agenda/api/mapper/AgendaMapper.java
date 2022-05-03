package ddev.com.agenda.api.mapper;

import ddev.com.agenda.api.request.AgendaResquest;
import ddev.com.agenda.api.response.AgendaResponse;
import ddev.com.agenda.domain.entity.Agenda;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AgendaMapper {

    private final ModelMapper mapper;

    public Agenda toAgenda (AgendaResquest resquest){
        return mapper.map(resquest,Agenda.class);
    }
    public AgendaResponse toAgendaResponse(Agenda agenda){
        return mapper.map(agenda, AgendaResponse.class);
    }
    public List<AgendaResponse> toAgendaResponseList(List<Agenda> agendas){
        return agendas.stream()
                .map(this::toAgendaResponse)
                .collect(Collectors.toList());
    }
}
