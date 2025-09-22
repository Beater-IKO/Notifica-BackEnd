package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.AuditLog;
import br.com.bd_notifica.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    // repositório para logs de auditoria
    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    // registra uma ação no sistema para auditoria
    @Transactional
    public void logAction(String action, String entityType, Long entityId, Long userId, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setUserId(userId);
        auditLog.setDetails(details);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    // busca todas as ações de um usuário específico
    @Transactional(readOnly = true)
    public List<AuditLog> getUserActions(Long userId) {
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    // busca histórico de uma entidade específica
    @Transactional(readOnly = true)
    public List<AuditLog> getEntityHistory(String entityType, Long entityId) {
        return auditLogRepository.findByEntityTypeAndEntityIdOrderByTimestampDesc(entityType, entityId);
    }

    // busca ações em um período de tempo
    @Transactional(readOnly = true)
    public List<AuditLog> getActionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return auditLogRepository.findByTimestampBetweenOrderByTimestampDesc(start, end);
    }
}