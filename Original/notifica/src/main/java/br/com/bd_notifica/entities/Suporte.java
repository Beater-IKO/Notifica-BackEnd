package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

// Chamados de suporte técnico
@Entity
@Data
@Table(name = "suportes")
public class Suporte {

    // ID do chamado
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Título do problema
    @NotBlank(message = "O título não pode estar em branco")
    @Column(name = "titulo")
    private String titulo;

    // Descrição detalhada
    @NotBlank(message = "A descrição não pode estar em branco")
    @Column(name = "descricao")
    private String descricao;

    // Tipo de suporte (TECNICO, URGENTE, etc)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoSuporte tipo;

    // Status do atendimento
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // Quando foi criado
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Quem abriu o chamado
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("suportes")
    private User user;
}