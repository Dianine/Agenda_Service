package ddev.com.agenda.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private LocalDateTime horario;
    private LocalDateTime dataCriacao;

    @ManyToOne
    private Paciente paciente;
}
