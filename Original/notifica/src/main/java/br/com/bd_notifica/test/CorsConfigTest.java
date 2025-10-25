package main.java.br.com.bd_notifica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    private CorsConfig corsConfig;

    @BeforeEach
    void setUp() {
        // Instancia a classe antes de cada teste
        corsConfig = new CorsConfig();
    }

    @Test
    void testCorsConfigurationBeanNotNull() {
        // Testa se o método corsConfigurationSource() não retorna nulo
        UrlBasedCorsConfigurationSource source = corsConfig.corsConfigurationSource();
        assertNotNull(source, "A configuração de CORS não deveria ser nula");
    }

    @Test
    void testCorsConfigurationContainsExpectedSettings() {
        // Recupera a configuração gerada
        UrlBasedCorsConfigurationSource source = corsConfig.corsConfigurationSource();
        CorsConfiguration config = source.getCorsConfigurations().get("/");

        assertNotNull(config, "Deve existir uma configuração CORS para o caminho '/'");
        assertTrue(config.getAllowedOriginPatterns().contains("*") || config.getAllowedOrigins().contains("*"));
        assertTrue(config.getAllowedMethods().contains("GET"));
        assertTrue(config.getAllowedMethods().contains("POST"));
    }
}
