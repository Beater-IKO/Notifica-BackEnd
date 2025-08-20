package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


// Mensagens de retorno dos tickets
@Entity
@Table(name = "mensagens_retorno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemRetorno {

    // ID da mensagem
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Conteúdo da mensagem
    @NotBlank(message = "O conteudo não pode estar em branco")
    private String conteudo;

    // Data de envio
    private LocalDateTime dataEnvio = LocalDateTime.now();

    // Usuário que enviou
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"mensagens", "tickets"})
    private User usuario;

    // Ticket relacionado
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonIgnoreProperties({"mensagens"})
    private Ticket ticket;
}
