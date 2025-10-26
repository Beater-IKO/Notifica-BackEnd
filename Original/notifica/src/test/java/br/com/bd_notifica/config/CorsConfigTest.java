package br.com.bd_notifica.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CorsConfigTest {

    private CorsConfig corsConfig;
    
    @Mock
    private CorsRegistry corsRegistry;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        corsConfig = new CorsConfig();
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de validação de instância não nula")
    void testCorsConfigurationNotNull() {
        assertNotNull(corsConfig, "A configuração de CORS não deveria ser nula");
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de existência do método addCorsMappings")
    void testAddCorsMappingsMethodExists() {
        // Testa se o método addCorsMappings existe na classe
        assertDoesNotThrow(() -> {
            corsConfig.getClass().getMethod("addCorsMappings", CorsRegistry.class);
        });
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Cenário de validação de implementação WebMvcConfigurer")
    void testCorsConfigImplementsWebMvcConfigurer() {
        assertTrue(corsConfig instanceof org.springframework.web.servlet.config.annotation.WebMvcConfigurer);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO – Cenário de configuração CORS com registry real")
    void testAddCorsMappingsWithRealRegistry() {
        org.springframework.web.servlet.config.annotation.CorsRegistry realRegistry = 
            new org.springframework.web.servlet.config.annotation.CorsRegistry();
        
        assertDoesNotThrow(() -> corsConfig.addCorsMappings(realRegistry));
    }
}
