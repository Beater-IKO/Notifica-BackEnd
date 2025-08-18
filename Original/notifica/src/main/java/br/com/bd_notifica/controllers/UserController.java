package br.com.bd_notifica.controllers;


import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

    // Serviço de usuários
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Criar novo usuário
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody User user) { 
        try {
            var result = userService.save(user);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace(); // Mostra erro no console
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Listar todos os usuários
    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        try{
            var result = userService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar por ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id){
        try {
            var result = userService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar por nome (ignora maiúscula/minúscula)
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<User>> findByNome(@PathVariable String nome){
        try {
            var result = userService.findByNome(nome);    
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar por tipo de usuário
    @GetMapping("/findByRole/{role}")
    public ResponseEntity<List<User>> findByRole(@PathVariable String role){
        try {
            var result = userService.findByRole(UserRole.valueOf(role));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Atualizar usuário
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@Valid @PathVariable Integer id, @RequestBody User userUpdated){
        try {
            var result = userService.update(id, userUpdated);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    // Excluir usuário (não pode excluir admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Integer id){
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}