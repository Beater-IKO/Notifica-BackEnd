package br.com.bd_notifica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.bd_notifica.repositories.UserRepository;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/clear-users")
    public ResponseEntity<?> clearUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok("Todos os usuários foram removidos");
    }

    @GetMapping("/list-users")
    public ResponseEntity<?> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}