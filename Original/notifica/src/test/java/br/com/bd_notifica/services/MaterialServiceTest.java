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
import br.com.bd_notifica.entities.Material;
import br.com.bd_notifica.repositories.MaterialRepository;
import br.com.bd_notifica.services.MaterialService;

@ExtendWith(MockitoExtension.class)
public class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialService materialService;

    private Material testMaterial;

    @BeforeEach
    void setUp() {
        testMaterial = new Material();
        testMaterial.setId(1);
        testMaterial.setNome("Material Teste");
        testMaterial.setDescricao("Descrição do material");
        testMaterial.setQuantidadeEstoque(10);
    }

    @Test
    void testSaveValidMaterial() {
        when(materialRepository.save(any(Material.class))).thenReturn(testMaterial);

        Material savedMaterial = materialService.save(testMaterial);

        assertNotNull(savedMaterial);
        assertEquals(testMaterial.getId(), savedMaterial.getId());
        assertEquals(testMaterial.getNome(), savedMaterial.getNome());
        assertEquals(testMaterial.getDescricao(), savedMaterial.getDescricao());
        assertEquals(testMaterial.getQuantidadeEstoque(), savedMaterial.getQuantidadeEstoque());
        verify(materialRepository).save(any(Material.class));
    }

    @Test
    void testFindByIdSuccess() {
        when(materialRepository.findById(1)).thenReturn(Optional.of(testMaterial));

        Material foundMaterial = materialService.findById(1);

        assertNotNull(foundMaterial);
        assertEquals(testMaterial.getId(), foundMaterial.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(materialRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> materialService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Material> materialList = Arrays.asList(testMaterial);
        when(materialRepository.findAll()).thenReturn(materialList);

        List<Material> result = materialService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindByNome() {
        List<Material> materialList = Arrays.asList(testMaterial);
        when(materialRepository.findByNomeIgnoreCase("Material Teste")).thenReturn(materialList);

        List<Material> result = materialService.findByNome("Material Teste");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindByQuantidadeEstoqueGreaterThan() {
        List<Material> materialList = Arrays.asList(testMaterial);
        when(materialRepository.findByQuantidadeEstoqueGreaterThan(5)).thenReturn(materialList);

        List<Material> result = materialService.findByQuantidadeEstoqueGreaterThan(5);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateMaterialSuccess() {
        when(materialRepository.findById(1)).thenReturn(Optional.of(testMaterial));
        when(materialRepository.save(any(Material.class))).thenReturn(testMaterial);

        Material updatedMaterial = new Material();
        updatedMaterial.setNome("Material Atualizado");
        
        Material result = materialService.update(1, updatedMaterial);

        assertNotNull(result);
        assertEquals("Material Atualizado", result.getNome());
    }

    @Test
    void testDeleteMaterial() {
        when(materialRepository.findById(1)).thenReturn(Optional.of(testMaterial));

        assertDoesNotThrow(() -> materialService.delete(1));
        verify(materialRepository).delete(testMaterial);
    }

    @Test
    void testUpdateMaterialWithAllFields() {
        when(materialRepository.findById(1)).thenReturn(Optional.of(testMaterial));
        when(materialRepository.save(any(Material.class))).thenReturn(testMaterial);

        Material updatedMaterial = new Material();
        updatedMaterial.setNome("Novo Nome");
        updatedMaterial.setDescricao("Nova Descrição");
        updatedMaterial.setQuantidadeEstoque(20);
        
        Material result = materialService.update(1, updatedMaterial);

        assertNotNull(result);
        assertEquals("Novo Nome", result.getNome());
        assertEquals("Nova Descrição", result.getDescricao());
        assertEquals(20, result.getQuantidadeEstoque());
    }

    @Test
    void testUpdateMaterialWithNullFields() {
        when(materialRepository.findById(1)).thenReturn(Optional.of(testMaterial));
        when(materialRepository.save(any(Material.class))).thenReturn(testMaterial);

        Material updatedMaterial = new Material();
        // Todos os campos null - não devem alterar o material original
        
        Material result = materialService.update(1, updatedMaterial);

        assertNotNull(result);
        assertEquals(testMaterial.getNome(), result.getNome());
        assertEquals(testMaterial.getDescricao(), result.getDescricao());
        assertEquals(testMaterial.getQuantidadeEstoque(), result.getQuantidadeEstoque());
    }
}
