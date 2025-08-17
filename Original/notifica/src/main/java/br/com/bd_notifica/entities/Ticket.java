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


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "O problema não pode ser nulo")
    @Column(name = "problema")
    private String problema;

    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private GrauDePrioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties("ticket")
    private Categoria categoria;

    @OneToMany(mappedBy = "ticket")
    private List<MensagemRetorno> mensagens;

}
