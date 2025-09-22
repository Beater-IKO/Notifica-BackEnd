package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.SystemConfig;
import br.com.bd_notifica.services.SystemConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins = "http://localhost:60058")
public class SystemConfigController {

    private final SystemConfigService configService;

    public SystemConfigController(SystemConfigService configService) {
        this.configService = configService;
    }

    @GetMapping
    public ResponseEntity<List<SystemConfig>> getAllConfigs() {
        return ResponseEntity.ok(configService.getAllConfigs());
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getConfig(@PathVariable String key, @RequestParam(required = false) String defaultValue) {
        String value = configService.getConfigValue(key, defaultValue);
        return ResponseEntity.ok(value);
    }

    @PostMapping
    public ResponseEntity<?> setConfig(@RequestBody Map<String, Object> request) {
        try {
            String key = request.get("key").toString();
            String value = request.get("value").toString();
            String description = request.getOrDefault("description", "").toString();
            Long userId = Long.valueOf(request.get("userId").toString());

            configService.setConfigValue(key, value, description, userId);
            return ResponseEntity.ok("Configuração salva com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar configuração: " + e.getMessage());
        }
    }
}