package br.com.bd_notifica.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthDebugController {

    @GetMapping("/auth-info")
    public Map<String, Object> getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            return Map.of(
                "authenticated", false,
                "message", "Nenhuma autenticação encontrada"
            );
        }
        
        return Map.of(
            "authenticated", auth.isAuthenticated(),
            "principal", auth.getPrincipal().toString(),
            "authorities", auth.getAuthorities().toString(),
            "name", auth.getName(),
            "principalClass", auth.getPrincipal().getClass().getSimpleName()
        );
    }
    
    @GetMapping("/users")
    public Map<String, Object> listUsers() {
        try {
            // Injetar UserService via Spring Context
            org.springframework.context.ApplicationContext context = 
                org.springframework.web.context.support.WebApplicationContextUtils
                    .getWebApplicationContext(((org.springframework.web.context.request.ServletRequestAttributes) 
                        org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes())
                        .getRequest().getServletContext());
            
            if (context != null) {
                br.com.bd_notifica.services.UserService userService = context.getBean(br.com.bd_notifica.services.UserService.class);
                var users = userService.findAll();
                
                var userList = users.stream().map(user -> Map.of(
                    "id", user.getId(),
                    "nome", user.getNome(),
                    "email", user.getEmail(),
                    "role", user.getRole() != null ? user.getRole().name() : "NULL",
                    "senhaHash", user.getSenha() != null ? user.getSenha().substring(0, Math.min(20, user.getSenha().length())) + "..." : "NULL"
                )).toList();
                
                return Map.of(
                    "totalUsers", users.size(),
                    "users", userList
                );
            }
            
            return Map.of("error", "Context não encontrado");
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
    
    @GetMapping("/tickets")
    public Map<String, Object> debugTickets() {
        try {
            org.springframework.context.ApplicationContext context = 
                org.springframework.web.context.support.WebApplicationContextUtils
                    .getWebApplicationContext(((org.springframework.web.context.request.ServletRequestAttributes) 
                        org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes())
                        .getRequest().getServletContext());
            
            if (context != null) {
                javax.sql.DataSource dataSource = context.getBean(javax.sql.DataSource.class);
                
                try (java.sql.Connection conn = dataSource.getConnection();
                     java.sql.Statement stmt = conn.createStatement();
                     java.sql.ResultSet rs = stmt.executeQuery("SELECT id, problema, descricao, status, sala_id, user_id FROM ticket")) {
                    
                    java.util.List<Map<String, Object>> ticketList = new java.util.ArrayList<>();
                    
                    while (rs.next()) {
                        ticketList.add(Map.of(
                            "id", rs.getInt("id"),
                            "problema", rs.getString("problema") != null ? rs.getString("problema") : "N/A",
                            "descricao", rs.getString("descricao") != null ? rs.getString("descricao") : "N/A",
                            "status", rs.getString("status") != null ? rs.getString("status") : "N/A",
                            "salaId", rs.getObject("sala_id"),
                            "userId", rs.getObject("user_id")
                        ));
                    }
                    
                    return Map.of(
                        "totalTickets", ticketList.size(),
                        "tickets", ticketList
                    );
                }
            }
            
            return Map.of("error", "Context não encontrado");
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
    
    @GetMapping("/salas")
    public Map<String, Object> debugSalas() {
        try {
            org.springframework.context.ApplicationContext context = 
                org.springframework.web.context.support.WebApplicationContextUtils
                    .getWebApplicationContext(((org.springframework.web.context.request.ServletRequestAttributes) 
                        org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes())
                        .getRequest().getServletContext());
            
            if (context != null) {
                br.com.bd_notifica.services.SalaService salaService = context.getBean(br.com.bd_notifica.services.SalaService.class);
                var salas = salaService.findAll();
                
                return Map.of(
                    "totalSalas", salas.size(),
                    "salas", salas
                );
            }
            
            return Map.of("error", "Context não encontrado");
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}