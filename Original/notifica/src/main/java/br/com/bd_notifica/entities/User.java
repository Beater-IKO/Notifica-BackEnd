package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.bd_notifica.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// Usuários do sistema - dados pessoais e relacionamentos
@Entity
@Data
@Table(name = "usuarios")
public class User {

    // ID único
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Nome completo
    @NotBlank(message = "Nome não pode estar em branco")
    @Column(name = "nome")
    private String nome;

    // CPF único
    @NotBlank(message = "O CPF não pode estar vazio")
    @Column(name = "CPF", length = 11, unique = true)
    private String cpf;

    // Email válido
    @Email(message = "O email precisa estar no formato correto")
    @Column(name = "email", nullable = false)
    private String email;

    // Nome de usuário para login
    @Column(name = "usuario", unique = true)
    private String usuario;

    // Senha
    @NotBlank(message = "Senha não pode estar vazia")
    @Column(name = "senha", nullable = false)
    private String senha;

    // Tipo de usuário (opcional, será definido como ESTUDANTE por padrão)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Sala do usuário
    @ManyToOne
    @JsonIgnoreProperties("alunos")
    @JoinColumn(name = "sala_id")
    private Sala sala;

    // Tickets criados pelo usuário
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Ticket> tickets = new ArrayList<>();

    // Protocolos de requisição
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Protocolo> protocolos = new ArrayList<>();

    // Chamados de suporte
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Suporte> suportes = new ArrayList<>();
}
