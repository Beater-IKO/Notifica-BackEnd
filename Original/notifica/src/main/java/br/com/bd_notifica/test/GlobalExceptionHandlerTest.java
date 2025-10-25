package main.java.br.com.bd_notifica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        // Instancia o handler antes de cada teste
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleGenericException() {
        // Cria uma exceção genérica simulada
        GenericException ex = new GenericException("Erro genérico de teste");

        // Chama o método do handler
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

        // Verifica se o retorno é 400 Bad Request
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro genérico de teste", response.getBody().getMessage());
    }

    @Test
    void testHandleValidationException() {
        // Simula uma exceção de validação
        ValidationException ex = new ValidationException("Campo inválido");

        // Executa o método correspondente
        ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex);

        // Espera código 422 Unprocessable Entity
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Campo inválido", response.getBody().getMessage());
    }

    @Test
    void testHandleGenericRuntimeException() {
        // Cria uma exceção comum (não específica)
        RuntimeException ex = new RuntimeException("Erro inesperado");

        // Executa o método genérico de tratamento
        ResponseEntity<ErrorResponse> response = handler.handleGenericRuntimeException(ex);

        // Espera retorno 500 Internal Server Error
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro inesperado", response.getBody().getMessage());
    }
}
