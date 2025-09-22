package br.com.bd_notifica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve estar em formato válido")
    private String email;
}