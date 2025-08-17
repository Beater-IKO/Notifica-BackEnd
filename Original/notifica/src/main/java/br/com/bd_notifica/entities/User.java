package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.bd_notifica.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Nome não pode estar em branco")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "O CPF não pode estar vazio")
    @Column(name = "CPF", length = 11, unique = true)
    private String cpf;

    @Email(message = "O email precisa estar no formato correto")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "Senha não pode estar vazia")
    @Column(name = "senha", nullable = false)
    private String senha;   

    @NotNull(message = "A role tem que estar no tipo correto")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JsonIgnoreProperties("alunos")
    @JoinColumn(name = "salas", nullable = false)
    private Sala sala;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Protocolo> protocolos = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Suporte> suportes = new ArrayList<>();
}
