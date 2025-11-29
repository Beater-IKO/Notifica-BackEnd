package br.com.bd_notifica;

import br.com.bd_notifica.config.SecurityTokenFilter;
import br.com.bd_notifica.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

  @Autowired
  private AuthenticationService authenticationService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            // --- ROTAS PÚBLICAS (Login/Registro) ---
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/debug/**").permitAll()
            .requestMatchers("/api/admin/clear-users").permitAll()
            .requestMatchers("/api/admin/list-users").permitAll()
            .requestMatchers("/api/tickets/public-test").permitAll()

            // --- ROTAS DE TICKETS - ESTUDANTE tem acesso completo ---
            .requestMatchers("/api/tickets/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR", "FUNCIONARIO", "ESTUDANTE")
            
            // --- ROTAS DE SALAS E CURSOS - ESTUDANTE pode ler ---
            .requestMatchers(HttpMethod.GET, "/api/salas/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR", "FUNCIONARIO", "ESTUDANTE")
            .requestMatchers(HttpMethod.POST, "/api/salas/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR")
            .requestMatchers(HttpMethod.PUT, "/api/salas/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR")
            .requestMatchers(HttpMethod.DELETE, "/api/salas/**").hasAnyRole("ADMIN", "GESTOR")
            
            .requestMatchers(HttpMethod.GET, "/api/cursos/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR", "FUNCIONARIO", "ESTUDANTE")
            .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR")
            .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasAnyRole("ADMIN", "GESTOR", "PROFESSOR")
            .requestMatchers(HttpMethod.DELETE, "/api/cursos/**").hasAnyRole("ADMIN", "GESTOR")

            // --- ROTAS DE USUÁRIOS (Manter restrição básica de Admin) ---
            .requestMatchers(HttpMethod.POST, "/api/usuarios/save").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
            // Para garantir leitura, vamos liberar para autenticados também caso dê erro
            .requestMatchers(HttpMethod.GET, "/api/usuarios/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").authenticated()

            // --- ROTAS DE SUPORTE ---
            .requestMatchers("/api/suporte/**").authenticated()

            // --- QUALQUER OUTRA ROTA ---
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
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(authenticationService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}