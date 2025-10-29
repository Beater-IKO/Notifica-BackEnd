package br.com.bd_notifica.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {

    @Test
    void testConstructorWithMessage() {
        ValidationException ex = new ValidationException("Erro de validação");
        assertEquals("Erro de validação", ex.getMessage());
    }

    @Test
    void testExceptionIsRuntimeException() {
        ValidationException ex = new ValidationException("Teste");
        assertTrue(ex instanceof RuntimeException);
    }

    @Test
    void testExceptionWithNullMessage() {
        ValidationException ex = new ValidationException(null);
        assertNull(ex.getMessage());
    }
}
