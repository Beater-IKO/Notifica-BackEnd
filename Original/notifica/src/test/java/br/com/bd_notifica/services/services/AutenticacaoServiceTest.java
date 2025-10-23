package br.com.bd_notifica.services.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.AutenticacaoService;

@ExtendWith(MockitoExtension.class)
public class AutenticacaoServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setNome("Test User");
        testUser.setUsuario("testuser");
        testUser.setSenha("password");
        testUser.setEmail("test@test.com");
        testUser.setCpf("123.456.789-00");
        testUser.setRole(UserRole.ESTUDANTE);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = autenticacaoService.loadUserByUsername("test@test.com");

        assertNotNull(userDetails);
        assertEquals(testUser.getEmail(), userDetails.getUsername());
        verify(userRepository).findByEmail("test@test.com");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, 
            () -> autenticacaoService.loadUserByUsername("nonexistent@test.com"));
    }
}