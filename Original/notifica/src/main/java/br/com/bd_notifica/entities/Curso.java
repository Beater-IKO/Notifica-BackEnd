package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

// Cursos oferecidos pela instituição
@Entity
@Data
@Table(name = "cursos")
public class Curso {

    // ID do curso
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Nome do curso
    @NotBlank(message = "O nome do curso não pode estar em branco")
    @Column(name = "nome")
    private String nome;

    // Duração em horas
    @NotNull(message = "A duração não pode ser vazia")
    @Column(name = "duracao")
    private int duracao;


    // Salas do curso
    @OneToMany(mappedBy = "curso")
    @JsonIgnoreProperties("curso")
    private List<Sala> salas;
}
