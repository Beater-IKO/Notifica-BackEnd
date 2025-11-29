package br.com.bd_notifica.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TicketsDebugController {

    // Chame: GET /api/tickets/debug
    @GetMapping("/api/tickets/debug")
    public ResponseEntity<Object> debug() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null)
            return ResponseEntity.ok(Map.of("authenticated", false));
        return ResponseEntity.ok(Map.of(
                "authenticated", a.isAuthenticated(),
                "principal", a.getPrincipal() == null ? "" : a.getPrincipal().toString(),
                "authorities", a.getAuthorities()));
    }

    // Novo endpoint: retorna todos os headers recebidos na requisição
    @GetMapping("/api/tickets/headers")
    public ResponseEntity<Map<String, String>> headers(@RequestHeader Map<String, String> headers) {
        // Retorna exatamente o mapa de headers (inclui Authorization se enviado)
        return ResponseEntity.ok(headers);
    }

    // Decodifica o payload do JWT (base64url) e retorna as claims + authentication
    // atual.
    // Útil para ver onde está definida a role do usuário.
    @GetMapping("/api/debug/claims")
    public ResponseEntity<Object> claims(@RequestHeader(value = "Authorization", required = false) String auth) {
        Map<String, Object> out = new HashMap<>();
        out.put("authorizationPresent", auth != null && !auth.isBlank());
        out.put("authorization", auth == null ? "" : (auth.length() > 120 ? auth.substring(0, 120) + "..." : auth));

        if (auth != null && auth.startsWith("Bearer ")) {
            try {
                String token = auth.substring(7);
                String[] parts = token.split("\\.");
                if (parts.length >= 2) {
                    String payload = parts[1];
                    byte[] decoded = java.util.Base64.getUrlDecoder().decode(padBase64(payload));
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode node = mapper.readTree(decoded);
                    out.put("claims", mapper.convertValue(node, Map.class));
                } else {
                    out.put("claims", Map.of("error", "token inválido (não possui 2+ partes)"));
                }
            } catch (Exception e) {
                out.put("claims", Map.of("error", "erro ao decodificar payload", "detail", e.getMessage()));
            }
        } else {
            out.put("claims", Map.of());
        }

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null) {
            out.put("authentication", Map.of("authenticated", false));
        } else {
            out.put("authentication", Map.of(
                    "authenticated", a.isAuthenticated(),
                    "principal", a.getPrincipal() == null ? "" : a.getPrincipal().toString(),
                    "authorities", a.getAuthorities()));
        }

        return ResponseEntity.ok(out);
    }

    // padding helper (igual ao usado no filtro)
    private static String padBase64(String base64) {
        int mod = base64.length() % 4;
        if (mod == 2)
            return base64 + "==";
        if (mod == 3)
            return base64 + "=";
        if (mod == 1)
            return base64 + "===";
        return base64;
    }
}
