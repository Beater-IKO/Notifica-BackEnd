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

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
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
        logger.info("Endpoint /api/auth/login foi chamado com email: " + dados.email());
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody User user) {
        if (user.getRole() == null) {
            user.setRole(br.com.bd_notifica.enums.UserRole.ESTUDANTE);
        }
        User savedUser = userService.save(user);
        return ResponseEntity.status(201).body("Usuário criado com sucesso");
    }
}