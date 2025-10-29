package br.com.bd_notifica.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.entities.MensagemRetorno;
import br.com.bd_notifica.repositories.MensagemRetornoRepository;
import br.com.bd_notifica.services.MensagemRetornoService;

@ExtendWith(MockitoExtension.class)
public class MensagemRetornoServiceTest {

    @Mock
    private MensagemRetornoRepository mensagemRetornoRepository;

    @InjectMocks
    private MensagemRetornoService mensagemRetornoService;

    private MensagemRetorno testMensagem;

    @BeforeEach
    void setUp() {
        testMensagem = new MensagemRetorno();
        testMensagem.setId(1);
        testMensagem.setConteudo("Mensagem de teste");
    }

    @Test
    void testSaveValidMensagem() {
        when(mensagemRetornoRepository.save(any(MensagemRetorno.class))).thenReturn(testMensagem);

        MensagemRetorno savedMensagem = mensagemRetornoService.save(testMensagem);

        assertNotNull(savedMensagem);
        assertEquals(testMensagem.getId(), savedMensagem.getId());
        assertEquals(testMensagem.getConteudo(), savedMensagem.getConteudo());
        verify(mensagemRetornoRepository).save(any(MensagemRetorno.class));
    }

    @Test
    void testFindByIdSuccess() {
        when(mensagemRetornoRepository.findById(1)).thenReturn(Optional.of(testMensagem));

        MensagemRetorno foundMensagem = mensagemRetornoService.findById(1);

        assertNotNull(foundMensagem);
        assertEquals(testMensagem.getId(), foundMensagem.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(mensagemRetornoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> mensagemRetornoService.findById(1));
    }

    @Test
    void testFindAll() {
        List<MensagemRetorno> mensagemList = Arrays.asList(testMensagem);
        when(mensagemRetornoRepository.findAll()).thenReturn(mensagemList);

        List<MensagemRetorno> result = mensagemRetornoService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindByTicket() {
        List<MensagemRetorno> mensagemList = Arrays.asList(testMensagem);
        when(mensagemRetornoRepository.findByTicketId(1)).thenReturn(mensagemList);

        List<MensagemRetorno> result = mensagemRetornoService.findByTicket(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindByUser() {
        List<MensagemRetorno> mensagemList = Arrays.asList(testMensagem);
        when(mensagemRetornoRepository.findByUsuario_NomeIgnoreCase("Test User")).thenReturn(mensagemList);

        List<MensagemRetorno> result = mensagemRetornoService.findByUser("Test User");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteMensagem() {
        when(mensagemRetornoRepository.findById(1)).thenReturn(Optional.of(testMensagem));

        assertDoesNotThrow(() -> mensagemRetornoService.delete(1));
        verify(mensagemRetornoRepository).delete(testMensagem);
    }
}
