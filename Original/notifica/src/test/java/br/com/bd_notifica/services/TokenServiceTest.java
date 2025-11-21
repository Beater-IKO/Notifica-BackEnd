package br.com.bd_notifica.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("TESTE DE UNIDADE – Cenário de geração de token JWT com sucesso")
    void testGerarTokenSuccess() {
        String token = tokenService.gerarToken(testUser);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário com usuário nulo que lança exceção RuntimeException")
    void testGerarTokenWithNullUser() {
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(null));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de geração de token com usuário sem email")
    void testGerarTokenWithUserWithoutEmail() {
        testUser.setEmail(null);
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(testUser));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de validação de secret vazio que lança exceção")
    void testGerarTokenWithEmptySecret() {
        ReflectionTestUtils.setField(tokenService, "secret", "");
        assertThrows(RuntimeException.class, () -> tokenService.gerarToken(testUser));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de validação de formato de token gerado")
    void testTokenFormat() {
        String token = tokenService.gerarToken(testUser);
        
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3, "Token JWT deve ter 3 partes separadas por ponto");
        assertFalse(token.isEmpty());
        assertTrue(token.length() > 50, "Token deve ter tamanho mínimo");
    }
}
