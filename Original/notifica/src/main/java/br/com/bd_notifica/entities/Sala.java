package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.bd_notifica.enums.Andar;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

// Salas de aula da instituição
@Entity
@Data
@Table(name = "salas")
public class Sala {

    // ID da sala
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Número da sala
    @NotNull(message = "Número da sala não pode ser vazio")
    @Column(name = "numero")
    private String numero;

    // Andar onde fica a sala
    @NotNull(message = "Andar deve existir")
    @Column(name = "Andar")
    private Andar andar;

    // Alunos da sala
    @JsonIgnore
    @OneToMany(mappedBy = "sala")
    @JsonIgnoreProperties("sala")
    private List<User> alunos;

    // Professores da sala
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "sala_professor", joinColumns = @JoinColumn(name = "sala_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnoreProperties("salasProfessor")
    private List<User> professores;

    // Curso da sala
    @ManyToOne
    @JoinColumn(name = "curso_id")
    @JsonIgnoreProperties("salas")
    private Curso curso;

    // Tickets da sala
    @JsonIgnore
    @OneToMany(mappedBy = "sala")
    private List<Ticket> tickets;
}
