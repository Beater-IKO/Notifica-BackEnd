package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "mensagens_retorno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemRetorno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O conteudo não pode estar em branco")
    private String conteudo;

    private LocalDateTime dataEnvio = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"mensagens", "tickets"})
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonIgnoreProperties({"mensagens"})
    private Ticket ticket;
}
