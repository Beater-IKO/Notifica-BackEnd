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
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.bd_notifica.config.GenericExceptions.AlreadyExists;
import br.com.bd_notifica.config.GenericExceptions.InvalidData;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.config.GenericExceptions.Unauthorized;
import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.SalaRepository;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.UserService;





@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private Sala testSala;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setNome("fabio augusto lorencini");
        testUser.setUsuario("Fabio");
        testUser.setSenha("Senha");
        testUser.setEmail("test@test.com");
        testUser.setCpf("123.456.789-00");
        testUser.setRole(UserRole.ESTUDANTE);

        testSala = new Sala();
        testSala.setId(1);
    }

    @Test
    void testSaveValidUser() {
        when(userRepository.findByUsuario(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertNotNull(savedUser);
        assertEquals(testUser.getId(), savedUser.getId());
        assertEquals(testUser.getNome(), savedUser.getNome());
        assertEquals(testUser.getUsuario(), savedUser.getUsuario());
        assertEquals(testUser.getEmail(), savedUser.getEmail());
        assertEquals(testUser.getCpf(), savedUser.getCpf());
        assertEquals(testUser.getRole(), savedUser.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSaveUserWithInvalidCPF() {
        testUser.setCpf("invalid-cpf");
        assertThrows(InvalidData.class, () -> userService.save(testUser));
    }

    @Test
    void testFindByIdSuccess() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        User foundUser = userService.findById(1);

        assertNotNull(foundUser);
        assertEquals(testUser.getId(), foundUser.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> userService.findById(1));
    }

    @Test
    void testFindAll() {
        List<User> userList = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteAdminUser() {
        testUser.setRole(UserRole.ADMIN);
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        assertThrows(Unauthorized.class, () -> userService.delete(1));
    }

    @Test
    void testUpdateUserSuccess() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setNome("Updated Name");
        
        User result = userService.update(1, updatedUser);

        assertNotNull(result);
        assertEquals("Updated Name", result.getNome());
    }

    @Test
    void testFindByRole() {
        List<User> userList = Arrays.asList(testUser);
        when(userRepository.findByRole(UserRole.ESTUDANTE)).thenReturn(userList);

        List<User> result = userService.findByRole(UserRole.ESTUDANTE);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(UserRole.ESTUDANTE, result.get(0).getRole());
    }

    @Test
    void testSaveUserWithInvalidEmail() {
        testUser.setEmail("invalid-email");
        assertThrows(InvalidData.class, () -> userService.save(testUser));
    }

    @Test
    void testSaveUserWithExistingUsuario() {
        when(userRepository.findByUsuario(testUser.getUsuario())).thenReturn(testUser);
        assertThrows(AlreadyExists.class, () -> userService.save(testUser));
    }

    @Test
    void testSaveUserWithSala() {
        testUser.setSala(testSala);
        when(userRepository.findByUsuario(anyString())).thenReturn(null);
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertNotNull(savedUser);
        assertEquals(testSala, savedUser.getSala());
    }

    @Test
    void testSaveUserWithInvalidSala() {
        testUser.setSala(testSala);
        when(userRepository.findByUsuario(anyString())).thenReturn(null);
        when(salaRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(NotFound.class, () -> userService.save(testUser));
    }

    @Test
    void testSaveUserWithNullRole() {
        testUser.setRole(null);
        when(userRepository.findByUsuario(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User savedUser = userService.save(testUser);

        assertEquals(UserRole.ESTUDANTE, savedUser.getRole());
    }

    @Test
    void testUpdateUserWithAllFields() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(salaRepository.findById(1)).thenReturn(Optional.of(testSala));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setNome("Updated Name");
        updatedUser.setCpf("987.654.321-00");
        updatedUser.setEmail("updated@test.com");
        updatedUser.setSenha("newPassword");
        updatedUser.setRole(UserRole.ADMIN);
        updatedUser.setSala(testSala);
        
        User result = userService.update(1, updatedUser);

        assertNotNull(result);
    }

    @Test
    void testUpdateUserRemoveSala() {
        testUser.setSala(testSala);
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setSala(null);
        
        User result = userService.update(1, updatedUser);

        assertNull(result.getSala());
    }

    @Test
    void testUpdateUserWithInvalidSala() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(salaRepository.findById(1)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setSala(testSala);
        
        assertThrows(NotFound.class, () -> userService.update(1, updatedUser));
    }

    @Test
    void testDeleteNonAdminUser() {
        testUser.setRole(UserRole.ESTUDANTE);
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        
        assertDoesNotThrow(() -> userService.delete(1));
        verify(userRepository).delete(testUser);
    }

    @Test
    void testFindByNome() {
        List<User> userList = Arrays.asList(testUser);
        when(userRepository.findByNomeIgnoreCase("fabio")).thenReturn(userList);

        List<User> result = userService.findByNome("fabio");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testAuthenticate() {
        when(userRepository.findByUsuarioAndSenha("Fabio", "Senha")).thenReturn(testUser);

        User result = userService.authenticate("Fabio", "Senha");

        assertNotNull(result);
        assertEquals(testUser, result);
    }

    @Test
    void testValidCPFFormats() {
        // Teste CPF com pontos e hífen
        testUser.setCpf("123.456.789-00");
        when(userRepository.findByUsuario(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        assertDoesNotThrow(() -> userService.save(testUser));
        
        // Teste CPF apenas números
        testUser.setCpf("12345678900");
        assertDoesNotThrow(() -> userService.save(testUser));
    }

    @Test
    void testValidEmail() {
        testUser.setEmail("valid@email.com");
        when(userRepository.findByUsuario(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        assertDoesNotThrow(() -> userService.save(testUser));
    }

    @Test
    void testUpdateWithBlankFields() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = new User();
        updatedUser.setNome("");
        updatedUser.setCpf(" ");
        updatedUser.setEmail("\t");
        updatedUser.setSenha("\n");
        
        User result = userService.update(1, updatedUser);

        // Campos em branco não devem alterar os valores originais
        assertEquals(testUser.getNome(), result.getNome());
        assertEquals(testUser.getCpf(), result.getCpf());
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void testSaveUserWithEmptyEmail() {
        testUser.setEmail("");
        assertThrows(InvalidData.class, () -> userService.save(testUser));
    }
}