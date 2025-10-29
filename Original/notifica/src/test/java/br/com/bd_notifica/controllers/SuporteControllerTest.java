package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.services.SuporteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SuporteControllerTest {

    @Mock
    private SuporteService suporteService;

    @InjectMocks
    private SuporteController suporteController;

    private Suporte suporte;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setNome("Augusto");

        suporte = new Suporte();
        suporte.setId(100);
        suporte.setTitulo("Erro ao fazer login");
        suporte.setDescricao("O sistema retorna erro 500 ao tentar logar.");
        suporte.setTipo(TipoSuporte.TECNICO);
        suporte.setStatus(Status.EM_ANDAMENTO);
        suporte.setDataCriacao(LocalDateTime.now());
        suporte.setUser(user);
    }

    @Test
    void deveSalvarChamadoDeSuporte() {
        when(suporteService.save(suporte)).thenReturn(suporte);

        ResponseEntity<?> response = suporteController.save(suporte);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(suporte, response.getBody());
        verify(suporteService, times(1)).save(suporte);
    }

    @Test
    void deveListarTodosOsChamados() {
        when(suporteService.findAll()).thenReturn(List.of(suporte));

        ResponseEntity<List<Suporte>> response = suporteController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Erro ao fazer login", response.getBody().get(0).getTitulo());
        verify(suporteService, times(1)).findAll();
    }

    @Test
    void deveBuscarChamadoPorId() {
        when(suporteService.findById(100)).thenReturn(suporte);

        ResponseEntity<Suporte> response = suporteController.findById(100);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.EM_ANDAMENTO, response.getBody().getStatus());
        verify(suporteService, times(1)).findById(100);
    }

    @Test
    void deveBuscarPorTipoDeSuporte() {
        when(suporteService.findByTipo(TipoSuporte.TECNICO)).thenReturn(List.of(suporte));

        ResponseEntity<List<Suporte>> response = suporteController.findByTipo("TECNICO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TipoSuporte.TECNICO, response.getBody().get(0).getTipo());
        verify(suporteService, times(1)).findByTipo(TipoSuporte.TECNICO);
    }

    @Test
    void deveBuscarPorStatus() {
        when(suporteService.findByStatus(Status.EM_ANDAMENTO)).thenReturn(List.of(suporte));

        ResponseEntity<List<Suporte>> response = suporteController.findByStatus("EM_ANDAMENTO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Status.EM_ANDAMENTO, response.getBody().get(0).getStatus());
        verify(suporteService, times(1)).findByStatus(Status.EM_ANDAMENTO);
    }

    @Test
    void deveAtualizarChamadoDeSuporte() {
        when(suporteService.update(100, suporte)).thenReturn(suporte);

        ResponseEntity<Suporte> response = suporteController.update(100, suporte);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(suporte, response.getBody());
        verify(suporteService, times(1)).update(100, suporte);
    }

    @Test
    void deveExcluirChamadoDeSuporte() {
        doNothing().when(suporteService).delete(100);

        ResponseEntity<Suporte> response = suporteController.delete(100);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(suporteService, times(1)).delete(100);
    }
}
