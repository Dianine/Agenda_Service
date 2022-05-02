package ddev.com.agenda.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;

}