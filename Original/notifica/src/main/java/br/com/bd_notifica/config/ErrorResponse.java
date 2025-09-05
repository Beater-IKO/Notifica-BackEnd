package main.java.br.com.bd_notifica.config;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String erro;
    private String mensagem;
}
