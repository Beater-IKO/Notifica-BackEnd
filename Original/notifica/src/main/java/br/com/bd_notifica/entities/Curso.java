package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O nome do curso não pode estar em branco")
    @Column(name = "nome")
    private String nome;

    @NotNull(message = "O número não pode ser vazio")
    @Column(name = "duração")
    private int duracao;


    @OneToMany(mappedBy = "curso")
    @JsonIgnoreProperties("curso")
    private List<Sala> salas;
}
