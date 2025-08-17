package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.bd_notifica.enums.Andar;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Número da sala não pode ser vazio")
    @Column(name = "numero")
    private String numero;

    @NotNull(message = "Andar deve existir")
    @Column(name = "Andar")
    private Andar andar;

    @OneToMany(mappedBy = "sala")
    @JsonIgnoreProperties("sala")
    private List<User> alunos;

    @ManyToMany
    @JoinTable(
            name = "sala_professor",
            joinColumns = @JoinColumn(name = "sala_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("salasProfessor")
    private List<User> professores;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @NotNull(message = "A sala precisa estar vinculada a um curso")
    @JsonIgnoreProperties("salas")
    private Curso curso;
}
