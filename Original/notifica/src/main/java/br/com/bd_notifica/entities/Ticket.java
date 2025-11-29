package br.com.bd_notifica.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.bd_notifica.enums.GrauDePrioridade;
import br.com.bd_notifica.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Tickets/chamados do sistema
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    // ID do ticket
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Titulo do Ticket
    @NotBlank(message = "O problema não pode ser nulo")
    @Column(name = "problema")
    private String problema;

    @Column(name = "descricao")
    private String descricao;

    // Prioridade do chamado
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private GrauDePrioridade prioridade;

    // Status atual
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // Quem criou o ticket
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("ticket")
    private User user;

    // Sala onde ocorreu o problema (obrigatório)
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    @JsonIgnoreProperties("tickets")
    private Sala sala;

    // Categoria do problema
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("ticket")
    private Categoria categoria;

    // Mensagens de retorno
    @OneToMany(mappedBy = "ticket")
    private List<MensagemRetorno> mensagens;

}
