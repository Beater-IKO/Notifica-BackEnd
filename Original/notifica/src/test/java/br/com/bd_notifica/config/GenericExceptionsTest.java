package br.com.bd_notifica.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenericExceptionsTest {

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de criação de exceção NotFound com mensagem")
    void testNotFoundExceptionWithMessage() {
        GenericExceptions.NotFound exception = new GenericExceptions.NotFound("Recurso não encontrado");
        assertEquals("Recurso não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de criação de exceção AlreadyExists com mensagem")
    void testAlreadyExistsExceptionWithMessage() {
        GenericExceptions.AlreadyExists exception = new GenericExceptions.AlreadyExists("Recurso já existe");
        assertEquals("Recurso já existe", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de criação de exceção InvalidData com mensagem")
    void testInvalidDataExceptionWithMessage() {
        GenericExceptions.InvalidData exception = new GenericExceptions.InvalidData("Dados inválidos");
        assertEquals("Dados inválidos", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de criação de exceção Unauthorized com mensagem")
    void testUnauthorizedExceptionWithMessage() {
        GenericExceptions.Unauthorized exception = new GenericExceptions.Unauthorized("Não autorizado");
        assertEquals("Não autorizado", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de criação de exceção General com mensagem")
    void testGeneralExceptionWithMessage() {
        GenericExceptions.General exception = new GenericExceptions.General("Erro geral");
        assertEquals("Erro geral", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de instância da classe GenericExceptions")
    void testGenericExceptionsInstance() {
        GenericExceptions instance = new GenericExceptions();
        assertNotNull(instance);
    }
}
