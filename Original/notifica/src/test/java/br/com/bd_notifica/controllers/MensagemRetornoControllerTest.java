package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.MensagemRetorno;
import br.com.bd_notifica.services.MensagemRetornoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tipo de teste: Teste Unitário
 * Cenário: Verifica se o MensagemRetornoController chama corretamente os métodos do service
 *          e retorna as respostas HTTP esperadas. As dependências são mockadas com Mockito.
 */
@ExtendWith(MockitoExtension.class)
public class MensagemRetornoControllerTest {

    @Mock
    private MensagemRetornoService mensagemRetornoService;

    @InjectMocks
    private MensagemRetornoController mensagemRetornoController;

    private MensagemRetorno mensagem;

    @BeforeEach
    void setUp() {
        mensagem = new MensagemRetorno();
        mensagem.setId(1);
        mensagem.setConteudo("Mensagem de teste");
    }

    @Test
    void deveSalvarMensagemERetornarCreated() {
        when(mensagemRetornoService.save(mensagem)).thenReturn(mensagem);

        ResponseEntity<?> resposta = mensagemRetornoController.save(mensagem);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(mensagem, resposta.getBody());
        verify(mensagemRetornoService).save(mensagem);
    }

    @Test
    void deveListarTodasMensagens() {
        List<MensagemRetorno> lista = Arrays.asList(mensagem);
        when(mensagemRetornoService.findAll()).thenReturn(lista);

        ResponseEntity<List<MensagemRetorno>> resposta = mensagemRetornoController.findAll();

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(lista, resposta.getBody());
        verify(mensagemRetornoService).findAll();
    }

    @Test
    void deveBuscarMensagemPorId() {
        when(mensagemRetornoService.findById(1)).thenReturn(mensagem);

        ResponseEntity<MensagemRetorno> resposta = mensagemRetornoController.findById(1);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(mensagem, resposta.getBody());
        verify(mensagemRetornoService).findById(1);
    }

    @Test
    void deveBuscarMensagensPorTicket() {
        List<MensagemRetorno> lista = Arrays.asList(mensagem);
        when(mensagemRetornoService.findByTicket(5)).thenReturn(lista);

        ResponseEntity<List<MensagemRetorno>> resposta = mensagemRetornoController.findByTicket(5);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(lista, resposta.getBody());
        verify(mensagemRetornoService).findByTicket(5);
    }

    @Test
    void deveBuscarMensagensPorUsuario() {
        List<MensagemRetorno> lista = Arrays.asList(mensagem);
        when(mensagemRetornoService.findByUser("Augusto")).thenReturn(lista);

        ResponseEntity<List<MensagemRetorno>> resposta = mensagemRetornoController.findByUser("Augusto");

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(lista, resposta.getBody());
        verify(mensagemRetornoService).findByUser("Augusto");
    }

    @Test
    void deveDeletarMensagem() {
        doNothing().when(mensagemRetornoService).delete(1);

        ResponseEntity<MensagemRetorno> resposta = mensagemRetornoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(mensagemRetornoService).delete(1);
    }
}
