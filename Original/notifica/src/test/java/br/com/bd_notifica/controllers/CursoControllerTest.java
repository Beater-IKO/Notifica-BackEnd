package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.services.CursoService;

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
public class CursoControllerTest {

    @Mock
    private CursoService cursoService;

    @InjectMocks
    private CursoController cursoController;

    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso();
        curso.setId(1);
        curso.setNome("Engenharia de Software");
    }

    @Test
    void testSaveCurso() {
        when(cursoService.save(any(Curso.class))).thenReturn(curso);

        ResponseEntity<?> response = cursoController.save(curso);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(curso, response.getBody());
        verify(cursoService, times(1)).save(any(Curso.class));
    }

    @Test
    void testFindAll() {
        List<Curso> lista = Arrays.asList(curso, new Curso());
        when(cursoService.findAll()).thenReturn(lista);

        ResponseEntity<List<Curso>> response = cursoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(cursoService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(cursoService.findById(1)).thenReturn(curso);

        ResponseEntity<Curso> response = cursoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Engenharia de Software", response.getBody().getNome());
        verify(cursoService, times(1)).findById(1);
    }

    @Test
    void testUpdateCurso() {
        Curso atualizado = new Curso();
        atualizado.setId(1);
        atualizado.setNome("ADS");

        when(cursoService.update(eq(1), any(Curso.class))).thenReturn(atualizado);

        ResponseEntity<Curso> response = cursoController.update(1, atualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ADS", response.getBody().getNome());
        verify(cursoService, times(1)).update(eq(1), any(Curso.class));
    }

    @Test
    void testDeleteCurso() {
        doNothing().when(cursoService).delete(1);

        ResponseEntity<Curso> response = cursoController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cursoService, times(1)).delete(1);
    }
}
