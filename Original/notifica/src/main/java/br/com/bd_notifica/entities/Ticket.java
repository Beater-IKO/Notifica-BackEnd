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

    @NotBlank(message = "A area não pode ser nula")
    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private Area area;

    @NotBlank(message = "A prioridade não pode ser nula")
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private GrauDePrioridade prioridade;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "O status não pode ser vazio")
    @Column(name = "status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    
    @JsonIgnoreProperties("ticket")
    private Categoria categoria;

    @OneToMany(mappedBy = "ticket")
    private List<MensagemRetorno> mensagens;

}
