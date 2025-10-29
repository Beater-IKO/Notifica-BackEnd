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
import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.entities.Material;
import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;
import br.com.bd_notifica.repositories.ProtocoloRepository;
import br.com.bd_notifica.services.ProtocoloService;

@ExtendWith(MockitoExtension.class)
public class ProtocoloServiceTest {

    @Mock
    private ProtocoloRepository protocoloRepository;

    @InjectMocks
    private ProtocoloService protocoloService;

    private Protocolo testProtocolo;
    private Material testMaterial;

    @BeforeEach
    void setUp() {
        testMaterial = new Material();
        testMaterial.setId(1);
        testMaterial.setNome("Material Teste");

        testProtocolo = new Protocolo();
        testProtocolo.setId(1);
        testProtocolo.setDescricao("Protocolo de teste");
        testProtocolo.setQuantidadeSolicitada(5);
        testProtocolo.setMaterial(testMaterial);
        testProtocolo.setStatus(StatusProtocolo.PENDENTE);
    }

    @Test
    void testSaveValidProtocolo() {
        when(protocoloRepository.save(any(Protocolo.class))).thenReturn(testProtocolo);

        Protocolo savedProtocolo = protocoloService.save(testProtocolo);

        assertNotNull(savedProtocolo);
        assertEquals(testProtocolo.getId(), savedProtocolo.getId());
        assertEquals(testProtocolo.getDescricao(), savedProtocolo.getDescricao());
        assertEquals(testProtocolo.getQuantidadeSolicitada(), savedProtocolo.getQuantidadeSolicitada());
        verify(protocoloRepository).save(any(Protocolo.class));
    }

    @Test
    void testSaveProtocoloWithoutMaterial() {
        testProtocolo.setMaterial(null);
        assertThrows(ValidationException.class, () -> protocoloService.save(testProtocolo));
    }

    @Test
    void testSaveProtocoloWithObservacoes() {
        testProtocolo.setObservacoes("Observação teste");
        when(protocoloRepository.save(any(Protocolo.class))).thenReturn(testProtocolo);

        Protocolo savedProtocolo = protocoloService.save(testProtocolo);

        assertEquals(StatusProtocolo.EM_ANALISE, savedProtocolo.getStatus());
    }

    @Test
    void testFindByIdSuccess() {
        when(protocoloRepository.findById(1)).thenReturn(Optional.of(testProtocolo));

        Protocolo foundProtocolo = protocoloService.findById(1);

        assertNotNull(foundProtocolo);
        assertEquals(testProtocolo.getId(), foundProtocolo.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(protocoloRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> protocoloService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Protocolo> protocoloList = Arrays.asList(testProtocolo);
        when(protocoloRepository.findAll()).thenReturn(protocoloList);

        List<Protocolo> result = protocoloService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindByStatus() {
        List<Protocolo> protocoloList = Arrays.asList(testProtocolo);
        when(protocoloRepository.findByStatus(StatusProtocolo.PENDENTE)).thenReturn(protocoloList);

        List<Protocolo> result = protocoloService.findByStatus(StatusProtocolo.PENDENTE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(StatusProtocolo.PENDENTE, result.get(0).getStatus());
    }

    @Test
    void testFindByUserId() {
        List<Protocolo> protocoloList = Arrays.asList(testProtocolo);
        when(protocoloRepository.findByUserId(1)).thenReturn(protocoloList);

        List<Protocolo> result = protocoloService.findByUserId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateProtocoloSuccess() {
        when(protocoloRepository.findById(1)).thenReturn(Optional.of(testProtocolo));
        when(protocoloRepository.save(any(Protocolo.class))).thenReturn(testProtocolo);

        Protocolo updatedProtocolo = new Protocolo();
        updatedProtocolo.setDescricao("Descrição Atualizada");
        
        Protocolo result = protocoloService.update(1, updatedProtocolo);

        assertNotNull(result);
        assertEquals("Descrição Atualizada", result.getDescricao());
    }

    @Test
    void testDeleteProtocolo() {
        when(protocoloRepository.findById(1)).thenReturn(Optional.of(testProtocolo));

        assertDoesNotThrow(() -> protocoloService.delete(1));
        verify(protocoloRepository).delete(testProtocolo);
    }

    @Test
    void testSaveProtocoloWithNullStatus() {
        testProtocolo.setStatus(null);
        when(protocoloRepository.save(any(Protocolo.class))).thenReturn(testProtocolo);

        Protocolo savedProtocolo = protocoloService.save(testProtocolo);

        assertEquals(StatusProtocolo.PENDENTE, savedProtocolo.getStatus());
    }

    @Test
    void testUpdateProtocoloWithAllFields() {
        when(protocoloRepository.findById(1)).thenReturn(Optional.of(testProtocolo));
        when(protocoloRepository.save(any(Protocolo.class))).thenReturn(testProtocolo);

        Protocolo updatedProtocolo = new Protocolo();
        updatedProtocolo.setDescricao("Nova Descrição");
        updatedProtocolo.setQuantidadeSolicitada(10);
        updatedProtocolo.setObservacoes("Novas observações");
        updatedProtocolo.setStatus(StatusProtocolo.APROVADO);
        updatedProtocolo.setMaterial(testMaterial);
        
        Protocolo result = protocoloService.update(1, updatedProtocolo);

        assertNotNull(result);
        assertEquals("Nova Descrição", result.getDescricao());
        assertEquals(10, result.getQuantidadeSolicitada());
        assertEquals("Novas observações", result.getObservacoes());
        assertEquals(StatusProtocolo.APROVADO, result.getStatus());
        assertEquals(testMaterial, result.getMaterial());
    }

    @Test
    void testUpdateProtocoloWithNullFields() {
        when(protocoloRepository.findById(1)).thenReturn(Optional.of(testProtocolo));
        when(protocoloRepository.save(any(Protocolo.class))).thenReturn(testProtocolo);

        Protocolo updatedProtocolo = new Protocolo();
        // Campos null não devem alterar os valores originais
        
        Protocolo result = protocoloService.update(1, updatedProtocolo);

        assertNotNull(result);
        assertEquals(testProtocolo.getDescricao(), result.getDescricao());
        assertEquals(testProtocolo.getQuantidadeSolicitada(), result.getQuantidadeSolicitada());
        assertEquals(testProtocolo.getStatus(), result.getStatus());
    }
}
