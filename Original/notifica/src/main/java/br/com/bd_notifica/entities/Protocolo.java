package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.bd_notifica.enums.StatusProtocolo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "protocolos")
public class Protocolo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "A descrição da requisição não pode estar em branco")
    @Column(name = "descricao")
    private String descricao;

    @NotNull(message = "A quantidade solicitada não pode ser nula")
    @Column(name = "quantidade_solicitada")
    private Integer quantidadeSolicitada;

    @Column(name = "observacoes")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProtocolo status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("protocolos")
    private User user;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @JsonIgnoreProperties("protocolos")
    private Material material;
}