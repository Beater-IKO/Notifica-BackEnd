package br.com.bd_notifica.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.services.UserService;

import java.util.List;

/*
 * Controlador da API para gerenciar usuários do sistema
 * Aqui ficam todas as operações básicas: criar, listar, editar e apagar usuários
 * É tipo o "CRUD" dos usuários - as operações fundamentais
 */
@RestController // diz pro Spring que essa classe vai receber requisições HTTP
@RequestMapping("/api/users") // todas as URLs começam com /api/users
@CrossOrigin(origins = "http://localhost:4200") // permite que o front-end acesse a API
public class ApiUserController {

    // serviço que faz as operações com usuários no banco de dados
    private final UserService userService;

    // construtor que recebe o serviço de usuários
    // o Spring injeta automaticamente aqui
    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Lista todos os usuários cadastrados no sistema
     * É tipo ver a "agenda" completa de pessoas
     */
    @GetMapping // responde a GET em /api/users
    public ResponseEntity<List<User>> getAllUsers() {
        // busca todos os usuários no banco
        var result = userService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
     * Busca um usuário específico pelo ID
     * É tipo procurar uma pessoa pelo número da carteirinha
     */
    @GetMapping("/{id}") // responde a GET em /api/users/123
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        // pega o ID da URL e busca o usuário
        var result = userService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
     * Atualiza os dados de um usuário existente
     * É tipo editar o perfil de alguém que já está cadastrado
     */
    @PutMapping("/{id}") // responde a PUT em /api/users/123
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userUpdated) {
        // atualiza o usuário com os novos dados enviados
        var result = userService.update(id, userUpdated);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
     * Cria um usuário novo no sistema
     * É tipo fazer um cadastro novo na plataforma
     */
    @PostMapping // responde a POST em /api/users
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // salva o novo usuário no banco de dados
        var result = userService.save(user);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /*
     * Remove um usuário do sistema
     * É tipo cancelar a conta de alguém - não tem volta
     */
    @DeleteMapping("/{id}") // responde a DELETE em /api/users/123
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        // apaga o usuário do banco de dados
        userService.delete(id);
        // retorna status 204 (sem conteúdo) confirmando que foi apagado
        return ResponseEntity.noContent().build();
    }
}