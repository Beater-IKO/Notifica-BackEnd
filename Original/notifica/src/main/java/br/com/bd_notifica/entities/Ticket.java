package br.com.bd_notifica.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.GrauDePrioridade;
import br.com.bd_notifica.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    // Descrição do problema
    @NotBlank(message = "O problema não pode ser nulo")
    @Column(name = "problema")
    private String problema;

    // Área responsável
    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private Area area;

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
    private User user;

    // Categoria do problema
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("ticket")
    private Categoria categoria;

    // Mensagens de retorno
    @OneToMany(mappedBy = "ticket")
    private List<MensagemRetorno> mensagens;

}
