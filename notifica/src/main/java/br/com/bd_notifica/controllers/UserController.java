package br.com.bd_notifica.controllers;

import java.time.LocalDate;


import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.UserService;
import br.com.bd_notifica.utils.Criptografia;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserEntity autenticar(String email, String senha) {
        UserEntity user = userService.buscarPorEmail(email);
        if (user != null && Criptografia.verificarSenha(senha, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean registrar(String nome, String email, String senha, UserRole role) {
        if (userService.buscarPorEmail(email) != null) {
            return false; // já existe
        }

        UserEntity novo = new UserEntity();
        novo.setName(nome);
        novo.setEmail(email);
        novo.setPassword(Criptografia.gerarHash(senha));
        novo.setRole(role);
        novo.setCreateOnDate(LocalDate.now());

        userService.criarUser(novo);
        return true;
    }

}
