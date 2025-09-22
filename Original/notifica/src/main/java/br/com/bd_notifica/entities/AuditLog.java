package br.com.bd_notifica.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// entidade para registrar ações do sistema (auditoria)
@Entity
@Data
@Table(name = "audit_logs")
public class AuditLog {

    // ID único do log
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ação realizada (CREATE, UPDATE, DELETE, etc)
    @Column(name = "action", nullable = false)
    private String action;

    // tipo da entidade afetada (User, Ticket, etc)
    @Column(name = "entity_type", nullable = false)
    private String entityType;

    // ID da entidade afetada
    @Column(name = "entity_id")
    private Long entityId;

    // ID do usuário que realizou a ação
    @Column(name = "user_id")
    private Long userId;

    // detalhes adicionais da ação
    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    // quando a ação foi realizada
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}