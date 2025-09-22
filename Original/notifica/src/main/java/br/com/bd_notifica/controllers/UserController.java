package br.com.bd_notifica.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.UserService;
import jakarta.validation.Valid;

import java.util.List;

// API para gerenciar usuários
@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:60058")
public class UserController {

    // Serviço de usuários
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Criar novo usuário
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody User user) {
        var result = userService.save(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Listar todos os usuários
    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        var result = userService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        var result = userService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por nome (ignora maiúscula/minúscula)
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<User>> findByNome(@PathVariable String nome) {
        var result = userService.findByNome(nome);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por tipo de usuário
    @GetMapping("/findByRole/{role}")
    public ResponseEntity<List<User>> findByRole(@PathVariable String role) {
        var result = userService.findByRole(UserRole.valueOf(role));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Atualizar usuário
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@Valid @PathVariable Integer id, @RequestBody User userUpdated) {
        var result = userService.update(id, userUpdated);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Excluir usuário (não pode excluir admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}