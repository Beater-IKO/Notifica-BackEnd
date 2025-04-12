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

    // Getter e Setter para o campo 'status'
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ticket() {
    }

    public Ticket(Long id, String descricao, Area area, String sala, Prioridade prioridade, LocalDate dataCriacao,
            String status) {
        this.id = id;
        this.descricao = descricao;
        this.area = area;
        this.sala = sala;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.status = status; // Agora o status também pode ser atribuído no construtor
    }

    @Column(name = "aluno_id")
    private Long alunoId;

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", descricao=" + descricao + ", area=" + area + ", sala=" + sala + ", prioridade="
                + prioridade + ", dataCriacao=" + dataCriacao + ", status=" + status + ", alunoId=" + alunoId + "]";
    }

}
