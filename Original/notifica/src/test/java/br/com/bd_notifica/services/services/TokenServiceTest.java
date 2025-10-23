package br.com.bd_notifica.services.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.TokenService;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

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

        // Definindo o secret para os testes
        ReflectionTestUtils.setField(tokenService, "secret", "minha-chave-secreta-super-segura-para-jwt-tokens-123456789");
    }

    @Test
    void testGerarTokenSuccess() {
        String token = tokenService.gerarToken(testUser);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    void testGerarTokenWithNullUser() {
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(null));
    }

    @Test
    void testGerarTokenWithUserWithoutEmail() {
        testUser.setEmail(null);
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(testUser));
    }
}