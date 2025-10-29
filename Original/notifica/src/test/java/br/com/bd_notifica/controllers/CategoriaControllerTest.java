package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.services.CategoriaService;

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
public class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Infraestrutura");
    }

    @Test
    void testSaveCategoria() {
        when(categoriaService.save(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity<?> response = categoriaController.save(categoria);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoria, response.getBody());
        verify(categoriaService, times(1)).save(any(Categoria.class));
    }

    @Test
    void testFindAll() {
        List<Categoria> lista = Arrays.asList(categoria, new Categoria());
        when(categoriaService.findAll()).thenReturn(lista);

        ResponseEntity<List<Categoria>> response = categoriaController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(categoriaService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoriaService.findById(1)).thenReturn(categoria);

        ResponseEntity<Categoria> response = categoriaController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Infraestrutura", response.getBody().getNome());
        verify(categoriaService, times(1)).findById(1);
    }

    @Test
    void testFindByNome() {
        List<Categoria> lista = List.of(categoria);
        when(categoriaService.findByNome("Infraestrutura")).thenReturn(lista);

        ResponseEntity<List<Categoria>> response = categoriaController.findByNome("Infraestrutura");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(categoriaService, times(1)).findByNome("Infraestrutura");
    }

    @Test
    void testDeleteCategoria() {
        doNothing().when(categoriaService).delete(1);

        ResponseEntity<Categoria> response = categoriaController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoriaService, times(1)).delete(1);
    }
}
