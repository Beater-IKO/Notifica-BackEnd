package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.Andar;
import br.com.bd_notifica.services.SalaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SalaControllerTest {

    @Mock
    private SalaService salaService;

    @InjectMocks
    private SalaController salaController;

    private Sala sala;
    private Curso curso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        curso = new Curso();
        curso.setId(1);
        curso.setNome("Análise e Desenvolvimento de Sistemas");

        sala = new Sala();
        sala.setId(10);
        sala.setNumero("B203");
        sala.setAndar(Andar.SegundoAndar);
        sala.setCurso(curso);
    }

    @Test
    void deveSalvarSalaComSucesso() {
        when(salaService.save(sala)).thenReturn(sala);

        ResponseEntity<?> response = salaController.save(sala);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sala, response.getBody());
        verify(salaService, times(1)).save(sala);
    }

    @Test
    void deveListarTodasAsSalas() {
        when(salaService.findAll()).thenReturn(List.of(sala));

        ResponseEntity<List<Sala>> response = salaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("B203", response.getBody().get(0).getNumero());
        verify(salaService, times(1)).findAll();
    }

    @Test
    void deveBuscarSalaPorId() {
        when(salaService.findById(10)).thenReturn(sala);

        ResponseEntity<Sala> response = salaController.findById(10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Andar.SegundoAndar, response.getBody().getAndar());
        verify(salaService, times(1)).findById(10);
    }

    @Test
    void deveAtualizarSala() {
        when(salaService.update(10, sala)).thenReturn(sala);

        ResponseEntity<Sala> response = salaController.update(10, sala);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sala, response.getBody());
        verify(salaService, times(1)).update(10, sala);
    }

    @Test
    void deveExcluirSala() {
        doNothing().when(salaService).delete(10);

        ResponseEntity<Sala> response = salaController.delete(10);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(salaService, times(1)).delete(10);
    }
}
