package br.com.bd_notifica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bd_notifica.dto.LoginRequest;
import br.com.bd_notifica.dto.LoginResponse;
import br.com.bd_notifica.dto.ChangePasswordRequest;
import br.com.bd_notifica.dto.ResetPasswordRequest;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.services.UserService;
import br.com.bd_notifica.services.AuthService;

import jakarta.validation.Valid;

// controlador para autenticação e segurança
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:60058")
public class AuthController {

    // serviços necessários
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // endpoint para fazer login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getUsuario(), request.getSenha());
        if (user != null) {
            LoginResponse response = new LoginResponse();
            response.setId(user.getId());
            response.setNome(user.getNome());
            response.setRole(user.getRole().toString());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).build();
    }

    // endpoint para alterar senha
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, @RequestParam Integer userId) {
        try {
            authService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword(), request.getConfirmPassword());
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao alterar senha: " + e.getMessage());
        }
    }

    // endpoint para resetar senha
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request.getEmail());
            return ResponseEntity.ok("Email de recuperação enviado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao resetar senha: " + e.getMessage());
        }
    }

    // endpoint para validar sessão
    @PostMapping("/validate-session")
    public ResponseEntity<?> validateSession(@RequestParam String token) {
        try {
            boolean isValid = authService.validateSession(token);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}