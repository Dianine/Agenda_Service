package ddev.com.agenda.domain.service;

import ddev.com.agenda.domain.entity.Agenda;
import ddev.com.agenda.domain.entity.Paciente;
import ddev.com.agenda.domain.repository.AgendaRepository;
import ddev.com.agenda.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AgendaServiceTest {

    @InjectMocks //Classe que estou enjetando os Mocks
    AgendaService service;

    @Mock //Classes que serão mockadas
    PacienteService pacienteService;

    @Mock //Classes que serão mockadas
    AgendaRepository repository;

    @Captor //Teste de estado
    ArgumentCaptor<Agenda> agendaCaptor;

    @Test
    @DisplayName("Deve Salvar Agenda com Sucesso")
    void salvarComSucesso() {
        //Setup: Arrange
        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descirção do agendamentro");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(agenda.getPaciente().getId())).thenReturn(Optional.of(paciente));
        Mockito.when(repository.findByHorario(now)).thenReturn(Optional.empty());

        //Teste: Action
        service.salvar(agenda);

        //Validações: Assertions
        Mockito.verify(pacienteService).buscarPorId(agenda.getPaciente().getId());
        Mockito.verify(repository).findByHorario(now);

        Mockito.verify(repository).save(agendaCaptor.capture());
        Agenda agendaSalva = agendaCaptor.getValue();

        org.assertj.core.api.Assertions.assertThat(agendaSalva.getPaciente()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(agendaSalva.getDataCriacao()).isNotNull();

    }

    @Test
    @DisplayName("Não deve salvar agendamento sem paciente")
    void salvarErroPacienteNaoEncontrado() {

        LocalDateTime now = LocalDateTime.now();
        Agenda agenda = new Agenda();
        agenda.setDescricao("Descirção do agendamentro");
        agenda.setHorario(now);

        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Paciente");

        agenda.setPaciente(paciente);

        Mockito.when(pacienteService.buscarPorId(ArgumentMatchers.any())).thenReturn(Optional.empty());

        //action
        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            service.salvar(agenda);
        });

        org.assertj.core.api.Assertions.assertThat(businessException.getMessage()).isEqualTo("Paciente não encontrado");


    }
}