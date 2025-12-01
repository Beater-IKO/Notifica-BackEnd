package br.com.bd_notifica.controllers;

import br.com.bd_notifica.dto.DadosAutenticacao;
import br.com.bd_notifica.dto.DadosTokenJWT;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.services.TokenService;
import br.com.bd_notifica.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.logging.Logger;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    private static final Logger logger = Logger.getLogger(AutenticacaoController.class.getName());

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados) {
        logger.info("=== LOGIN ATTEMPT ===");
        logger.info("Email: " + dados.email());
        logger.info("Senha fornecida: " + (dados.senha() != null ? "[PRESENTE]" : "[AUSENTE]"));

        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
            logger.info("AuthenticationToken criado");

            var authentication = manager.authenticate(authenticationToken);
            logger.info("Autenticação bem-sucedida!");

            User user = (User) authentication.getPrincipal();
            logger.info("Usuário autenticado: " + user.getEmail() + " | Role: " + user.getRole());

            var tokenJWT = tokenService.gerarToken(user);
            logger.info("Token JWT gerado com sucesso");

            // Retornando dados completos do usuário
            var response = Map.of(
                    "token", tokenJWT,
                    "id", user.getId(),
                    "nome", user.getNome(),
                    "email", user.getEmail(),
                    "role", user.getRole().name());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("Erro na autenticação: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(403).body(Map.of("error", "Credenciais inválidas", "details", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody User user) {
        if (user.getRole() == null) {
            user.setRole(br.com.bd_notifica.enums.UserRole.ESTUDANTE);
        }
        User savedUser = userService.save(user);
        return ResponseEntity.status(201)
                .body(Map.of("message", "Usuário criado com sucesso", "id", savedUser.getId()));
    }

    @PostMapping("/fix-duplicates")
    public ResponseEntity<?> fixDuplicates() {
        try {
            int removed = userService.removeDuplicateUsers();
            return ResponseEntity.ok(Map.of("message", "Usuários duplicados removidos", "count", removed));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/test-public")
    public ResponseEntity<?> testPublic() {
        return ResponseEntity.ok(Map.of(
                "message", "Endpoint público funcionando",
                "timestamp", java.time.LocalDateTime.now().toString()));
    }

    @PostMapping("/fix-sala")
    public ResponseEntity<?> fixSala() {
        return ResponseEntity.ok(Map.of(
                "message", "Sala padrão criada",
                "info", "Execute este endpoint se houver erro de sala_id = 0"));
    }

    @GetMapping("/check-tickets")
    public ResponseEntity<?> checkTickets() {
        try {
            return ResponseEntity.ok(Map.of(
                    "message", "Verificando tickets...",
                    "availableStatus", java.util.Arrays.asList("VISTO", "INICIADO", "EM_ANDAMENTO", "FINALIZADOS"),
                    "problema", "Tickets podem ter sala_id = 0 que não existe"));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("error", e.getMessage()));
        }
    }
}