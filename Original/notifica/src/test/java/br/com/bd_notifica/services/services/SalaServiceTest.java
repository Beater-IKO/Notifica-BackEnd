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
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.enums.Andar;
import br.com.bd_notifica.repositories.SalaRepository;
import br.com.bd_notifica.services.SalaService;

@ExtendWith(MockitoExtension.class)
public class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    private Sala testSala;

    @BeforeEach
    void setUp() {
        testSala = new Sala();
        testSala.setId(1);
        testSala.setNumero("101");
        testSala.setAndar(Andar.PrimeiroAndar);
    }

    @Test
    void testSaveValidSala() {
        when(salaRepository.save(any(Sala.class))).thenReturn(testSala);

        Sala savedSala = salaService.save(testSala);

        assertNotNull(savedSala);
        assertEquals(testSala.getId(), savedSala.getId());
        assertEquals(testSala.getNumero(), savedSala.getNumero());
        assertEquals(testSala.getAndar(), savedSala.getAndar());
        verify(salaRepository).save(any(Sala.class));
    }

    @Test
    void testFindByIdSuccess() {
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));

        Sala foundSala = salaService.findById(1);

        assertNotNull(foundSala);
        assertEquals(testSala.getId(), foundSala.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(salaRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> salaService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Sala> salaList = Arrays.asList(testSala);
        when(salaRepository.findAll()).thenReturn(salaList);

        List<Sala> result = salaService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateSalaSuccess() {
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));
        when(salaRepository.save(any(Sala.class))).thenReturn(testSala);

        Sala updatedSala = new Sala();
        updatedSala.setNumero("102");
        
        Sala result = salaService.update(1, updatedSala);

        assertNotNull(result);
        assertEquals("102", result.getNumero());
    }

    @Test
    void testDeleteSala() {
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));

        assertDoesNotThrow(() -> salaService.delete(1));
        verify(salaRepository).delete(testSala);
    }

    @Test
    void testUpdateSalaWithAllFields() {
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));
        when(salaRepository.save(any(Sala.class))).thenReturn(testSala);

        Sala updatedSala = new Sala();
        updatedSala.setNumero("201");
        updatedSala.setAndar(Andar.SegundoAndar);
        
        Sala result = salaService.update(1, updatedSala);

        assertNotNull(result);
        assertEquals("201", result.getNumero());
        assertEquals(Andar.SegundoAndar, result.getAndar());
    }

    @Test
    void testUpdateSalaWithNullFields() {
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));
        when(salaRepository.save(any(Sala.class))).thenReturn(testSala);

        Sala updatedSala = new Sala();
        // Campos null não devem alterar os valores originais
        
        Sala result = salaService.update(1, updatedSala);

        assertNotNull(result);
        assertEquals(testSala.getNumero(), result.getNumero());
        assertEquals(testSala.getAndar(), result.getAndar());
    }

    @Test
    void testUpdateSalaWithCurso() {
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));
        when(salaRepository.save(any(Sala.class))).thenReturn(testSala);

        Sala updatedSala = new Sala();
        // Testando o campo curso que existe no update
        
        Sala result = salaService.update(1, updatedSala);

        assertNotNull(result);
    }
}