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

    @Column(name = "status")
    private String status;

    @Column(name = "image_path") // NOVO CAMPO: Para armazenar o caminho da imagem
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public Ticket() {
    }

    public Ticket(Long id, String descricao, Area area, String sala, Prioridade prioridade, LocalDate dataCriacao,
            String status, UserEntity user, String imagePath) { // Construtor atualizado
        this.id = id;
        this.descricao = descricao;
        this.area = area;
        this.sala = sala;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.user = user;
        this.imagePath = imagePath; // Inicializa o novo campo
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
        this.imagePath = outro.imagePath; // Copia o novo campo
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

    public String getImagePath() { // NOVO GETTER
        return imagePath;
    }

    public void setImagePath(String imagePath) { // NOVO SETTER
        this.imagePath = imagePath;
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
            ", imagemPath='" + (imagePath != null ? imagePath : "N/A") + '\'' + // Adiciona ao toString
            ", usuário=" + (user != null ? user.getName() + "[" + user.getRole() + "]" : "nenhum") +
            '}';
}
}