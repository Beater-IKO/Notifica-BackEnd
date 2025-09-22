package br.com.bd_notifica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd_notifica.dto.LoginRequest;
import br.com.bd_notifica.dto.LoginResponse;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.services.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:60058")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

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
}