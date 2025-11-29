package br.com.bd_notifica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

// Nome explícito do bean para evitar conflito com br.com.bd_notifica.controllers.DebugController
@RestController("debugControllerAlt")
public class DebugController {

    @GetMapping("/api/debug/token")
    public ResponseEntity<Map<String, String>> token(
            @RequestHeader(value = "Authorization", required = false) String auth) {
        return ResponseEntity.ok(Map.of("authorization", auth == null ? "" : auth));
    }

    @GetMapping("/api/debug/auth")
    public ResponseEntity<Object> auth() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null)
            return ResponseEntity.ok(Map.of("authenticated", false));
        return ResponseEntity.ok(Map.of(
                "authenticated", a.isAuthenticated(),
                "principal", a.getPrincipal() == null ? "" : a.getPrincipal().toString(),
                "authorities", a.getAuthorities()));
    }
}
