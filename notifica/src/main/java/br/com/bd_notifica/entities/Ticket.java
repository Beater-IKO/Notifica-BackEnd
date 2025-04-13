package br.com.bd_notifica.entities;

import br.com.bd_notifica.enums.*;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private Area area;

    private String sala;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private Prioridade prioridade;

    private LocalDate dataCriacao;

    @Column(name = "status") // Novo campo 'status' adicionado
    private String status; // Campo de status, como String (pode ser "Pendente", "Em andamento", etc.)

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Ticket() {
    }

    public Ticket(Long id, String descricao, Area area, String sala, Prioridade prioridade, LocalDate dataCriacao,
            String status, UserEntity user) {
        this.id = id;
        this.descricao = descricao;
        this.area = area;
        this.sala = sala;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.user = user;
    }

    public Ticket(Ticket outro) {
        this.id = outro.id;
        this.descricao = outro.descricao;
        this.area = outro.area;
        this.sala = outro.sala;
        this.prioridade = outro.prioridade;
        this.dataCriacao = outro.dataCriacao;
        this.status = outro.status;
        this.user = outro.user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
    return "Ticket {" +
            "id=" + id +
            ", descrição='" + descricao + '\'' +
            ", sala='" + sala + '\'' +
            ", área=" + area +
            ", prioridade=" + prioridade +
            ", status=" + status +
            ", dataCriacao=" + dataCriacao +
            ", usuário=" + (user != null ? user.getName() + "[" + user.getRole() + "]" : "nenhum") +
            '}';
}

    
}
