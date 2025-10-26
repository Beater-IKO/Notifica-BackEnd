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
import br.com.bd_notifica.config.GenericExceptions.Unauthorized;
import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.repositories.SuporteRepository;
import br.com.bd_notifica.services.SuporteService;

@ExtendWith(MockitoExtension.class)
public class SuporteServiceTest {

    @Mock
    private SuporteRepository suporteRepository;

    @InjectMocks
    private SuporteService suporteService;

    private Suporte testSuport;

    @BeforeEach
    void setUp() {
        testSuport = new Suporte();
        testSuport.setId(1);
        testSuport.setTitulo("Suporte Teste");
        testSuport.setDescricao("Descrição do suporte");
        testSuport.setTipo(TipoSuporte.GERAL);
        testSuport.setStatus(Status.VISTO);
    }

    @Test
    void testSaveValidSuport() {
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        Suporte savedSuport = suporteService.save(testSuport);

        assertNotNull(savedSuport);
        assertEquals(testSuport.getId(), savedSuport.getId());
        assertEquals(testSuport.getTitulo(), savedSuport.getTitulo());
        assertEquals(testSuport.getDescricao(), savedSuport.getDescricao());
        assertEquals(testSuport.getTipo(), savedSuport.getTipo());
        verify(suporteRepository).save(any(Suporte.class));
    }

    @Test
    void testSaveSuportWithoutTitulo() {
        testSuport.setTitulo(null);
        assertThrows(ValidationException.class, () -> suporteService.save(testSuport));
    }

    @Test
    void testSaveSuportWithoutDescricao() {
        testSuport.setDescricao(null);
        assertThrows(ValidationException.class, () -> suporteService.save(testSuport));
    }

    @Test
    void testSaveSuportTecnico() {
        testSuport.setTipo(TipoSuporte.TECNICO);
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        Suporte savedSuport = suporteService.save(testSuport);

        assertEquals(Status.EM_ANDAMENTO, savedSuport.getStatus());
    }

    @Test
    void testFindByIdSuccess() {
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));

        Suporte foundSuport = suporteService.findById(1);

        assertNotNull(foundSuport);
        assertEquals(testSuport.getId(), foundSuport.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(suporteRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> suporteService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Suporte> suportList = Arrays.asList(testSuport);
        when(suporteRepository.findAll()).thenReturn(suportList);

        List<Suporte> result = suporteService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateFinalizedSuport() {
        testSuport.setStatus(Status.FINALIZADOS);
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));

        Suporte updatedSuport = new Suporte();
        updatedSuport.setTitulo("Novo título");

        assertThrows(Unauthorized.class, () -> suporteService.update(1, updatedSuport));
    }

    @Test
    void testDeleteSuportEmAndamento() {
        testSuport.setStatus(Status.EM_ANDAMENTO);
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));

        assertThrows(Unauthorized.class, () -> suporteService.delete(1));
    }

    @Test
    void testFindByTipo() {
        List<Suporte> suportList = Arrays.asList(testSuport);
        when(suporteRepository.findByTipo(TipoSuporte.GERAL)).thenReturn(suportList);

        List<Suporte> result = suporteService.findByTipo(TipoSuporte.GERAL);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(TipoSuporte.GERAL, result.get(0).getTipo());
    }

    @Test
    void testFindByStatus() {
        List<Suporte> suportList = Arrays.asList(testSuport);
        when(suporteRepository.findByStatus(Status.VISTO)).thenReturn(suportList);

        List<Suporte> result = suporteService.findByStatus(Status.VISTO);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(Status.VISTO, result.get(0).getStatus());
    }

    @Test
    void testSaveSuportWithBlankTitulo() {
        testSuport.setTitulo("");
        assertThrows(ValidationException.class, () -> suporteService.save(testSuport));
    }

    @Test
    void testSaveSuportWithBlankDescricao() {
        testSuport.setDescricao(" ");
        assertThrows(ValidationException.class, () -> suporteService.save(testSuport));
    }

    @Test
    void testSaveSuportWithNullStatus() {
        testSuport.setStatus(null);
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        Suporte savedSuport = suporteService.save(testSuport);

        assertEquals(Status.VISTO, savedSuport.getStatus());
    }

    @Test
    void testUpdateSuportSuccess() {
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        Suporte updatedSuport = new Suporte();
        updatedSuport.setTitulo("Título atualizado");
        updatedSuport.setDescricao("Descrição atualizada");
        
        Suporte result = suporteService.update(1, updatedSuport);

        assertNotNull(result);
        assertEquals("Título atualizado", result.getTitulo());
        assertEquals("Descrição atualizada", result.getDescricao());
    }

    @Test
    void testUpdateSuportWithAllFields() {
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        Suporte updatedSuport = new Suporte();
        updatedSuport.setTitulo("Novo título");
        updatedSuport.setDescricao("Nova descrição");
        updatedSuport.setTipo(TipoSuporte.TECNICO);
        updatedSuport.setStatus(Status.EM_ANDAMENTO);
        
        Suporte result = suporteService.update(1, updatedSuport);

        assertNotNull(result);
        assertEquals(TipoSuporte.TECNICO, result.getTipo());
        assertEquals(Status.EM_ANDAMENTO, result.getStatus());
    }

    @Test
    void testUpdateSuportWithBlankFields() {
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        Suporte updatedSuport = new Suporte();
        updatedSuport.setTitulo("");
        updatedSuport.setDescricao("\t");
        
        Suporte result = suporteService.update(1, updatedSuport);

        // Campos em branco não devem alterar os valores originais
        assertEquals(testSuport.getTitulo(), result.getTitulo());
        assertEquals(testSuport.getDescricao(), result.getDescricao());
    }

    @Test
    void testDeleteSuportSuccess() {
        testSuport.setStatus(Status.VISTO);
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));
        
        assertDoesNotThrow(() -> suporteService.delete(1));
        verify(suporteRepository).delete(testSuport);
    }

    @Test
    void testUpdateSuportWithUser() {
        when(suporteRepository.findById(1)).thenReturn(Optional.of(testSuport));
        when(suporteRepository.save(any(Suporte.class))).thenReturn(testSuport);

        User user = new User();
        user.setId(1);
        
        Suporte updatedSuport = new Suporte();
        updatedSuport.setUser(user);
        
        Suporte result = suporteService.update(1, updatedSuport);

        assertNotNull(result);
        assertEquals(user, result.getUser());
    }
}
