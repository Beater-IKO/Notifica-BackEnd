package br.com.bd_notifica;

import br.com.bd_notifica.config.SecurityTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private SecurityTokenFilter securityTokenFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            // Rotas públicas
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/admin/clear-users").permitAll()
            .requestMatchers("/api/admin/list-users").permitAll()
            // Rotas de usuários - apenas ADMIN pode criar/deletar (exceto registro público)
            .requestMatchers(HttpMethod.POST, "/api/usuarios/save").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("ADMIN", "GESTOR")
            .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAnyRole("ADMIN", "GESTOR")
            // Rotas de tickets - todos autenticados podem acessar
            .requestMatchers("/api/tickets/**").authenticated()
            // Rotas de suporte - apenas ADMIN e FUNCIONARIO
            .requestMatchers("/api/suporte/**").hasAnyRole("ADMIN", "FUNCIONARIO")
            // Rotas de salas e cursos - ADMIN, GESTOR e PROFESSOR
            .requestMatchers("/api/salas/**", "/api/cursos/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR")
            // Todas as outras rotas precisam de autenticação
            .anyRequest().authenticated())
        .addFilterBefore(securityTokenFilter, UsernamePasswordAuthenticationFilter.class);
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