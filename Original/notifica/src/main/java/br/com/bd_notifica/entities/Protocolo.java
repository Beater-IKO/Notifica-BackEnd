package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.bd_notifica.enums.StatusProtocolo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entidade Protocolo - Sistema de requisição de materiais
 * Gerencia solicitações de materiais pelos usuários com controle de status
 * Permite rastreamento desde a solicitação até a aprovação/rejeição
 */
@Entity
@Data
@Table(name = "protocolos")
public class Protocolo {

    // Identificador único do protocolo de requisição
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Descrição detalhada da necessidade do material solicitado
    @NotBlank(message = "A descrição da requisição não pode estar em branco")
    @Column(name = "descricao")
    private String descricao;

    // Quantidade de itens solicitados pelo usuário
    @NotNull(message = "A quantidade solicitada não pode ser nula")
    @Column(name = "quantidade_solicitada")
    private Integer quantidadeSolicitada;

    // Observações adicionais ou justificativas da solicitação
    @Column(name = "observacoes")
    private String observacoes;

    // Status do protocolo (PENDENTE, EM_ANALISE, APROVADO, REJEITADO)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProtocolo status;

    // Data e hora de criação do protocolo (definida automaticamente)
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Relacionamento N:1 - Usuário que fez a solicitação
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("protocolos")
    private User user;

    // Relacionamento N:1 - Material sendo solicitado
    @ManyToOne
    @JoinColumn(name = "material_id")
    @JsonIgnoreProperties("protocolos")
    private Material material;
}