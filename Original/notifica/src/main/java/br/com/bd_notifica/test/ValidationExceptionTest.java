package main.java.br.com.bd_notifica.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Cria exceção com mensagem
        ValidationException ex = new ValidationException("Erro de validação");

        // Verifica se mensagem foi atribuída
        assertEquals("Erro de validação", ex.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        // Cria exceção com mensagem e causa
        Throwable cause = new RuntimeException("Causa do erro");
        ValidationException ex = new ValidationException("Erro com causa", cause);

        // Verifica os valores
        assertEquals("Erro com causa", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
