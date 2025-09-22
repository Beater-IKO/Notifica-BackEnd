package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.SystemConfig;
import br.com.bd_notifica.repositories.SystemConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SystemConfigService {

    private final SystemConfigRepository configRepository;
    private final AuditService auditService;

    public SystemConfigService(SystemConfigRepository configRepository, AuditService auditService) {
        this.configRepository = configRepository;
        this.auditService = auditService;
    }

    @Transactional(readOnly = true)
    public String getConfigValue(String key, String defaultValue) {
        Optional<SystemConfig> config = configRepository.findByConfigKey(key);
        return config.map(SystemConfig::getConfigValue).orElse(defaultValue);
    }

    @Transactional
    public void setConfigValue(String key, String value, String description, Long userId) {
        Optional<SystemConfig> existingConfig = configRepository.findByConfigKey(key);
        
        SystemConfig config;
        if (existingConfig.isPresent()) {
            config = existingConfig.get();
            config.setConfigValue(value);
        } else {
            config = new SystemConfig();
            config.setConfigKey(key);
            config.setConfigValue(value);
            config.setDescription(description);
        }
        
        config.setUpdatedAt(LocalDateTime.now());
        config.setUpdatedBy(userId);
        
        configRepository.save(config);
        
        auditService.logAction("CONFIG_UPDATE", "SystemConfig", config.getId(), userId, 
            "Configuração alterada: " + key + " = " + value);
    }

    @Transactional(readOnly = true)
    public List<SystemConfig> getAllConfigs() {
        return configRepository.findAll();
    }
}