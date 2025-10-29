package br.com.bd_notifica.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.config.GenericExceptions.AlreadyExists;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.repositories.CategoriaRepository;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.CategoriaService;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria testCategoria;

    @BeforeEach
    void setUp() {
        testCategoria = new Categoria();
        testCategoria.setId(1);
        testCategoria.setNome("Categoria Teste");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Cenário de salvamento de categoria válida com repositories mockados")
    void testSaveValidCategoria() {
        when(categoriaRepository.findByNomeIgnoreCase(anyString())).thenReturn(Arrays.asList());
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(testCategoria);

        Categoria savedCategoria = categoriaService.save(testCategoria);

        assertNotNull(savedCategoria);
        assertEquals(testCategoria.getId(), savedCategoria.getId());
        assertEquals(testCategoria.getNome(), savedCategoria.getNome());
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Cenário de categoria com nome duplicado que lança exceção AlreadyExists")
    void testSaveCategoriaWithDuplicateName() {
        when(categoriaRepository.findByNomeIgnoreCase(anyString())).thenReturn(Arrays.asList(testCategoria));

        assertThrows(AlreadyExists.class, () -> categoriaService.save(testCategoria));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário com categoria nula que lança exceção IllegalArgumentException")
    void testSaveNullCategoria() {
        assertThrows(IllegalArgumentException.class, () -> categoriaService.save(null));
    }

    @Test
    void testFindByIdSuccess() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(testCategoria));

        Categoria foundCategoria = categoriaService.findById(1);

        assertNotNull(foundCategoria);
        assertEquals(testCategoria.getId(), foundCategoria.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(categoriaRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> categoriaService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Categoria> categoriaList = Arrays.asList(testCategoria);
        when(categoriaRepository.findAll()).thenReturn(categoriaList);

        List<Categoria> result = categoriaService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteCategoriaInUse() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(testCategoria));
        when(ticketRepository.findByCategoriaId(1)).thenReturn(Arrays.asList());

        assertDoesNotThrow(() -> categoriaService.delete(1));
        verify(categoriaRepository).delete(testCategoria);
    }

    @Test
    void testFindByNome() {
        List<Categoria> categoriaList = Arrays.asList(testCategoria);
        when(categoriaRepository.findByNomeIgnoreCase("Categoria Teste")).thenReturn(categoriaList);

        List<Categoria> result = categoriaService.findByNome("Categoria Teste");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Cenário de exclusão de categoria com tickets vinculados que lança exceção ValidationException")
    void testDeleteCategoriaWithTickets() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(testCategoria));
        when(ticketRepository.findByCategoriaId(1)).thenReturn(Arrays.asList(mock(br.com.bd_notifica.entities.Ticket.class))); // Simula tickets existentes

        assertThrows(ValidationException.class, () -> categoriaService.delete(1));
    }
}
