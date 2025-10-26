package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Material;
import br.com.bd_notifica.services.MaterialService;
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
 * Cenário: Valida o comportamento do MaterialController isolando o MaterialService com Mockito.
 */
@ExtendWith(MockitoExtension.class)
public class MaterialControllerTest {

    @Mock
    private MaterialService materialService;

    @InjectMocks
    private MaterialController materialController;

    private Material material;

    @BeforeEach
    void setUp() {
        material = new Material();
        material.setId(1);
        material.setNome("Caderno");
        material.setQuantidadeEstoque(50);
    }

    @Test
    void deveSalvarMaterialERetornarCreated() {
        when(materialService.save(material)).thenReturn(material);

        ResponseEntity<?> resposta = materialController.save(material);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals(material, resposta.getBody());
        verify(materialService).save(material);
    }

    @Test
    void deveListarTodosMateriais() {
        List<Material> lista = Arrays.asList(material);
        when(materialService.findAll()).thenReturn(lista);

        ResponseEntity<List<Material>> resposta = materialController.findAll();

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(lista, resposta.getBody());
        verify(materialService).findAll();
    }

    @Test
    void deveBuscarMaterialPorId() {
        when(materialService.findById(1)).thenReturn(material);

        ResponseEntity<Material> resposta = materialController.findById(1);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(material, resposta.getBody());
        verify(materialService).findById(1);
    }

    @Test
    void deveBuscarMateriaisPorNome() {
        List<Material> lista = Arrays.asList(material);
        when(materialService.findByNome("Caderno")).thenReturn(lista);

        ResponseEntity<List<Material>> resposta = materialController.findByNome("Caderno");

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(lista, resposta.getBody());
        verify(materialService).findByNome("Caderno");
    }

    @Test
    void deveBuscarMateriaisComEstoqueMaiorQue() {
        List<Material> lista = Arrays.asList(material);
        when(materialService.findByQuantidadeEstoqueGreaterThan(10)).thenReturn(lista);

        ResponseEntity<List<Material>> resposta = materialController.findByQuantidadeEstoqueGreaterThan(10);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(lista, resposta.getBody());
        verify(materialService).findByQuantidadeEstoqueGreaterThan(10);
    }

    @Test
    void deveAtualizarMaterial() {
        when(materialService.update(1, material)).thenReturn(material);

        ResponseEntity<Material> resposta = materialController.update(1, material);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(material, resposta.getBody());
        verify(materialService).update(1, material);
    }

    @Test
    void deveDeletarMaterial() {
        doNothing().when(materialService).delete(1);

        ResponseEntity<Material> resposta = materialController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(materialService).delete(1);
    }
}
