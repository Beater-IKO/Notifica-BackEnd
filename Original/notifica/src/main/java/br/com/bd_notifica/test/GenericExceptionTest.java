package main.java.br.com.bd_notifica.test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenericExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Cria a exceção com uma mensagem
        GenericException exception = new GenericException("Erro genérico");

        // Verifica se a mensagem é mantida
        assertEquals("Erro genérico", exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        // Cria uma exceção com causa
        Throwable cause = new RuntimeException("Causa original");
        GenericException exception = new GenericException("Erro causado", cause);

        // Verifica se mensagem e causa foram atribuídas
        assertEquals("Erro causado", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
