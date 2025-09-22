package br.com.bd_notifica.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private Integer id;
    private String nome;
    private String role;
}