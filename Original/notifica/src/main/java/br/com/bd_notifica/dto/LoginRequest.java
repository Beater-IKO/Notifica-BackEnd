package br.com.bd_notifica.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String usuario;
    private String senha;
}