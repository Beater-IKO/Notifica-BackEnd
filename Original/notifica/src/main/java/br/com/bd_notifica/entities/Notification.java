package br.com.bd_notifica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// entidade para notificações do sistema
@Entity
@Data
@Table(name = "notifications")
public class Notification {

    // ID único da notificação
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // usuário que receberá a notificação
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("notifications")
    private User user;

    // título da notificação
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    // conteúdo da mensagem
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    // tipo da notificação (info, aviso, sucesso, erro)
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    // se a notificação foi lida
    @Column(name = "is_read")
    private Boolean isRead = false;

    // quando foi criada
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // tipos de notificação disponíveis
    public enum NotificationType {
        INFO, WARNING, SUCCESS, ERROR
    }
}