package br.com.bd_notifica.controllers;

import br.com.bd_notifica.dto.DadosAutenticacao;
import br.com.bd_notifica.dto.DadosTokenJWT;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity efetuarLogin(@RequestBody DadosAutenticacao dados) {
        logger.info("Endpoint /api/auth/login foi chamado com email: " + dados.email());
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}