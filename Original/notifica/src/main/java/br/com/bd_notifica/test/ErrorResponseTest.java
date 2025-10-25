package main.java.br.com.bd_notifica.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void testErrorResponseConstructorAndGetters() {
        // Cria um ErrorResponse com mensagem e status
        ErrorResponse error = new ErrorResponse("Erro encontrado", 400);

        // Verifica se os valores foram atribuídos corretamente
        assertEquals("Erro encontrado", error.getMessage());
        assertEquals(400, error.getStatus());
    }

    @Test
    void testSetters() {
        // Cria objeto vazio e usa setters
        ErrorResponse error = new ErrorResponse();
        error.setMessage("Falha no servidor");
        error.setStatus(500);

        // Testa os valores
        assertEquals("Falha no servidor", error.getMessage());
        assertEquals(500, error.getStatus());
    }
}
