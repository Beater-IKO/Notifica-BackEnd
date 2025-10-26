package br.com.bd_notifica.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleNotFound() {
        GenericExceptions.NotFound ex = new GenericExceptions.NotFound("Recurso não encontrado");
        ResponseEntity<ErrorResponse> response = handler.handleUsuarioNotFound(ex);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Recurso não encontrado", response.getBody().getMessage());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void testHandleInvalidData() {
        GenericExceptions.InvalidData ex = new GenericExceptions.InvalidData("Dados inválidos");
        ResponseEntity<ErrorResponse> response = handler.handleInvalidData(ex);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados inválidos", response.getBody().getMessage());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void testHandleAlreadyExists() {
        GenericExceptions.AlreadyExists ex = new GenericExceptions.AlreadyExists("Recurso já existe");
        ResponseEntity<ErrorResponse> response = handler.handleAlreadyExists(ex);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Recurso já existe", response.getBody().getMessage());
        assertEquals(409, response.getBody().getStatus());
    }

    @Test
    void testHandleUnauthorized() {
        GenericExceptions.Unauthorized ex = new GenericExceptions.Unauthorized("Não autorizado");
        ResponseEntity<ErrorResponse> response = handler.handleUnauthorized(ex);
        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Não autorizado", response.getBody().getMessage());
        assertEquals(401, response.getBody().getStatus());
    }

    @Test
    void testHandleGeneral() {
        GenericExceptions.General ex = new GenericExceptions.General("Erro interno");
        ResponseEntity<ErrorResponse> response = handler.handleGeneral(ex);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno", response.getBody().getMessage());
        assertEquals(500, response.getBody().getStatus());
    }
}
