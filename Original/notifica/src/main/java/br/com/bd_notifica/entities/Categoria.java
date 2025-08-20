package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

// Categorias para classificar tickets
@Entity
@Data
@Table(name = "categorias")
public class Categoria {

    // ID da categoria
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Nome da categoria
    @JoinColumn(name = "nome")
    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    // Tickets desta categoria
    @OneToMany(mappedBy = "categoria")
    @JsonIgnoreProperties("categoria")
    private List<Ticket> tickets;
}
