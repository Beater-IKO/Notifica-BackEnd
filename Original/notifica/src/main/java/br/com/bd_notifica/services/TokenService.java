package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(User usuario) {
        if (usuario == null) {
            throw new RuntimeException("Usuário não pode ser nulo");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("Email do usuário não pode ser nulo ou vazio");
        }
        
        try {
            return Jwts.builder()
                    .issuer("API Notifica")
                    .subject(usuario.getEmail())
                    .expiration(Date.from(dataExpiracao()))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .compact();
        } catch (Exception exception){
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String validateToken(String token) {
        try {
            System.out.println("=== TOKEN VALIDATION DEBUG ===");
            System.out.println("Secret usado: " + secret);
            System.out.println("Token recebido: " + token.substring(0, Math.min(50, token.length())) + "...");
            
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            System.out.println("Token válido! Subject: " + claims.getSubject());
            System.out.println("Expiração: " + claims.getExpiration());
            return claims.getSubject();
        } catch (Exception exception) {
            System.out.println("Erro na validação do token: " + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}