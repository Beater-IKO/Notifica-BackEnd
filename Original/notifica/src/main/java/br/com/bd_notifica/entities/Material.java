package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "materiais")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O nome do material não pode estar em branco")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "A quantidade em estoque não pode ser nula")
    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @OneToMany(mappedBy = "material")
    @JsonIgnoreProperties("material")
    private List<Protocolo> protocolos;
}