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
            // LIBERAR OPTIONS PARA CORS PREFLIGHT
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // ROTAS PÚBLICAS
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/debug/**").permitAll()
            .requestMatchers("/api/admin/**").permitAll()
            .requestMatchers("/error").permitAll()
            // TODAS AS OUTRAS ROTAS REQUEREM AUTENTICAÇÃO
            .anyRequest().authenticated())
        .addFilterBefore(securityTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // A MÁGICA: Use setAllowedOriginPatterns("*") com allowCredentials(true)
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    
    // Libera todos os métodos (inclusive OPTIONS)
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH"));
    
    // Libera todos os headers (Authorization, x-auth-token, etc)
    configuration.setAllowedHeaders(Arrays.asList("*"));
    
    // Permite credenciais (cookies, headers de auth)
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
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