package br.com.bd_notifica.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de criação de ErrorResponse com todos os parâmetros")
    void testErrorResponseConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse error = new ErrorResponse(now, 400, "Bad Request", "Erro encontrado");

        assertEquals("Erro encontrado", error.getMessage());
        assertEquals(400, error.getStatus());
        assertEquals("Bad Request", error.getError());
        assertEquals(now, error.getTimestamp());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de validação dos setters do ErrorResponse")
    void testSetters() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse error = new ErrorResponse(now, 200, "OK", "Sucesso");
        
        error.setMessage("Falha no servidor");
        error.setStatus(500);
        error.setError("Internal Server Error");
        
        LocalDateTime newTime = LocalDateTime.now().plusMinutes(1);
        error.setTimestamp(newTime);

        assertEquals("Falha no servidor", error.getMessage());
        assertEquals(500, error.getStatus());
        assertEquals("Internal Server Error", error.getError());
        assertEquals(newTime, error.getTimestamp());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de construtor alternativo do ErrorResponse")
    void testAlternativeConstructor() {
        ErrorResponse error = new ErrorResponse("Erro", "Mensagem de erro");
        assertNotNull(error);
    }
}
