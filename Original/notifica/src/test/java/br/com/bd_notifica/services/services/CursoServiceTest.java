package br.com.bd_notifica.services.services;

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
import br.com.bd_notifica.config.GenericExceptions.AlreadyExists;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.repositories.CursoRepository;
import br.com.bd_notifica.services.CursoService;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoService cursoService;

    private Curso testCurso;

    @BeforeEach
    void setUp() {
        testCurso = new Curso();
        testCurso.setId(1);
        testCurso.setNome("Curso Teste");
        testCurso.setDuracao(4);
    }

    @Test
    void testSaveValidCurso() {
        when(cursoRepository.findByNomeIgnoreCase(anyString())).thenReturn(Arrays.asList());
        when(cursoRepository.save(any(Curso.class))).thenReturn(testCurso);

        Curso savedCurso = cursoService.save(testCurso);

        assertNotNull(savedCurso);
        assertEquals(testCurso.getId(), savedCurso.getId());
        assertEquals(testCurso.getNome(), savedCurso.getNome());
        assertEquals(testCurso.getDuracao(), savedCurso.getDuracao());
        verify(cursoRepository).save(any(Curso.class));
    }

    @Test
    void testSaveCursoWithDuplicateName() {
        when(cursoRepository.findByNomeIgnoreCase(anyString())).thenReturn(Arrays.asList(testCurso));

        assertThrows(AlreadyExists.class, () -> cursoService.save(testCurso));
    }

    @Test
    void testSaveNullCurso() {
        assertThrows(ValidationException.class, () -> cursoService.save(null));
    }

    @Test
    void testSaveCursoWithBlankName() {
        testCurso.setNome("");
        assertThrows(ValidationException.class, () -> cursoService.save(testCurso));
    }

    @Test
    void testFindByIdSuccess() {
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));

        Curso foundCurso = cursoService.findById(1);

        assertNotNull(foundCurso);
        assertEquals(testCurso.getId(), foundCurso.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(cursoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> cursoService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Curso> cursoList = Arrays.asList(testCurso);
        when(cursoRepository.findAll()).thenReturn(cursoList);

        List<Curso> result = cursoService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateCursoSuccess() {
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));
        when(cursoRepository.findByNomeIgnoreCase(anyString())).thenReturn(Arrays.asList());
        when(cursoRepository.save(any(Curso.class))).thenReturn(testCurso);

        Curso updatedCurso = new Curso();
        updatedCurso.setNome("Curso Atualizado");
        
        Curso result = cursoService.update(1, updatedCurso);

        assertNotNull(result);
        assertEquals("Curso Atualizado", result.getNome());
    }

    @Test
    void testDeleteCurso() {
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));

        assertDoesNotThrow(() -> cursoService.delete(1));
        verify(cursoRepository).delete(testCurso);
    }

    @Test
    void testUpdateCursoWithDuplicateName() {
        Curso existingCurso = new Curso();
        existingCurso.setId(2);
        existingCurso.setNome("Curso Duplicado");
        
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));
        when(cursoRepository.findByNomeIgnoreCase("Curso Duplicado")).thenReturn(Arrays.asList(existingCurso));

        Curso updatedCurso = new Curso();
        updatedCurso.setNome("Curso Duplicado");
        
        assertThrows(AlreadyExists.class, () -> cursoService.update(1, updatedCurso));
    }

    @Test
    void testUpdateCursoWithAllFields() {
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));
        when(cursoRepository.findByNomeIgnoreCase(anyString())).thenReturn(Arrays.asList());
        when(cursoRepository.save(any(Curso.class))).thenReturn(testCurso);

        Curso updatedCurso = new Curso();
        updatedCurso.setNome("Novo Nome");
        updatedCurso.setDuracao(6);
        
        Curso result = cursoService.update(1, updatedCurso);

        assertNotNull(result);
        assertEquals("Novo Nome", result.getNome());
        assertEquals(6, result.getDuracao());
    }

    @Test
    void testUpdateCursoWithNullFields() {
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));
        when(cursoRepository.save(any(Curso.class))).thenReturn(testCurso);

        Curso updatedCurso = new Curso();
        // Campos null não devem alterar os valores originais
        
        Curso result = cursoService.update(1, updatedCurso);

        assertNotNull(result);
        assertEquals(testCurso.getNome(), result.getNome());
        assertEquals(testCurso.getDuracao(), result.getDuracao());
    }

    @Test
    void testUpdateCursoWithSameName() {
        when(cursoRepository.findById(1)).thenReturn(Optional.of(testCurso));
        when(cursoRepository.save(any(Curso.class))).thenReturn(testCurso);

        Curso updatedCurso = new Curso();
        updatedCurso.setNome("Curso Teste"); // Mesmo nome
        
        Curso result = cursoService.update(1, updatedCurso);

        assertNotNull(result);
        assertEquals("Curso Teste", result.getNome());
    }
}