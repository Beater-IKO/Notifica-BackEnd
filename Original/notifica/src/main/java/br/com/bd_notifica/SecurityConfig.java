package br.com.bd_notifica; // Verifique se o nome do seu pacote está correto

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // Desabilita CSRF, pois usaremos uma abordagem stateless (sem sessão)
        .csrf(csrf -> csrf.disable())
        // Configura o CORS usando o bean 'corsConfigurationSource' abaixo
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        // Define a política de criação de sessão como STATELESS
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Autoriza todas as requisições HTTP - para simplificar durante o
        // desenvolvimento
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll());
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Permite requisições da origem do seu front-end
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    // Permite os métodos HTTP, incluindo o OPTIONS
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // Permite todos os cabeçalhos
    configuration.setAllowedHeaders(Arrays.asList("*"));
    // Permite o envio de credenciais (cookies, etc.)
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    // Aplica esta configuração para todas as rotas da sua aplicação
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}