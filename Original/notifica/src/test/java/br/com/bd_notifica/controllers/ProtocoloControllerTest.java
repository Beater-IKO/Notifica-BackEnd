package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;
import br.com.bd_notifica.services.ProtocoloService;

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

@ExtendWith(MockitoExtension.class)
public class ProtocoloControllerTest {

    @Mock
    private ProtocoloService protocoloService;

    @InjectMocks
    private ProtocoloController protocoloController;

    private Protocolo protocolo;

    @BeforeEach
    void setUp() {
        protocolo = new Protocolo();
        protocolo.setId(1);
        protocolo.setDescricao("Requisição de material");
        protocolo.setStatus(StatusProtocolo.PENDENTE);
    }

    @Test
    void testSaveProtocolo() {
        when(protocoloService.save(any(Protocolo.class))).thenReturn(protocolo);

        ResponseEntity<?> response = protocoloController.save(protocolo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(protocolo, response.getBody());
        verify(protocoloService, times(1)).save(any(Protocolo.class));
    }

    @Test
    void testFindAll() {
        List<Protocolo> lista = Arrays.asList(protocolo, new Protocolo());
        when(protocoloService.findAll()).thenReturn(lista);

        ResponseEntity<List<Protocolo>> response = protocoloController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(protocoloService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(protocoloService.findById(1)).thenReturn(protocolo);

        ResponseEntity<Protocolo> response = protocoloController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(StatusProtocolo.PENDENTE, response.getBody().getStatus());
        verify(protocoloService, times(1)).findById(1);
    }

    @Test
    void testFindByStatus() {
        List<Protocolo> lista = List.of(protocolo);
        when(protocoloService.findByStatus(StatusProtocolo.PENDENTE)).thenReturn(lista);

        ResponseEntity<List<Protocolo>> response = protocoloController.findByStatus("PENDENTE");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(StatusProtocolo.PENDENTE, response.getBody().get(0).getStatus());
        verify(protocoloService, times(1)).findByStatus(StatusProtocolo.PENDENTE);
    }

    @Test
    void testFindByUserId() {
        List<Protocolo> lista = List.of(protocolo);
        when(protocoloService.findByUserId(5)).thenReturn(lista);

        ResponseEntity<List<Protocolo>> response = protocoloController.findByUserId(5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(protocoloService, times(1)).findByUserId(5);
    }

    @Test
    void testUpdateProtocolo() {
        Protocolo atualizado = new Protocolo();
        atualizado.setId(1);
        atualizado.setDescricao("Requisição atualizada");
        atualizado.setStatus(StatusProtocolo.APROVADO);

        when(protocoloService.update(eq(1), any(Protocolo.class))).thenReturn(atualizado);

        ResponseEntity<Protocolo> response = protocoloController.update(1, atualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(StatusProtocolo.APROVADO, response.getBody().getStatus());
        verify(protocoloService, times(1)).update(eq(1), any(Protocolo.class));
    }

    @Test
    void testDeleteProtocolo() {
        doNothing().when(protocoloService).delete(1);

        ResponseEntity<Protocolo> response = protocoloController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(protocoloService, times(1)).delete(1);
    }
}
