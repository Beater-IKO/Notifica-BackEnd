package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "suportes")
public class Suporte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O título não pode estar em branco")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "A descrição não pode estar em branco")
    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoSuporte tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("suportes")
    private User user;
}