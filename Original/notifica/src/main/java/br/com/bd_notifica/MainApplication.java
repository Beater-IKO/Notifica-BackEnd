package br.com.bd_notifica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement; // 1. IMPORTE
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement // 2. ADICIONE ESTA ANOTAÇÃO
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

  // Substitui o bean simples por um FilterRegistrationBean para controlar a ordem
  @Bean
  public FilterRegistrationBean<OncePerRequestFilter> requestDebugFilterRegistration() {
    OncePerRequestFilter filter = new OncePerRequestFilter() {
      private final Logger log = LoggerFactory.getLogger("RequestDebug");

      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
        try {
          String auth = request.getHeader("Authorization");
          String authPreview = "";
          if (auth != null && !auth.isBlank()) {
            authPreview = auth.length() > 60 ? auth.substring(0, 60) + "..." : auth;
          }
          Authentication a = SecurityContextHolder.getContext().getAuthentication();
          String principal = a == null ? "null" : String.valueOf(a.getPrincipal());
          Object authorities = a == null ? "null" : a.getAuthorities();
          log.info("REQ {} {} | Authorization present: {} | authPreview: {} | principal: {} | authorities: {}",
              request.getMethod(), request.getRequestURI(),
              auth != null, authPreview, principal, authorities);
        } catch (Exception e) {
          log.warn("Erro no requestDebugFilter", e);
        }
        filterChain.doFilter(request, response);
      }
    };

    FilterRegistrationBean<OncePerRequestFilter> reg = new FilterRegistrationBean<>(filter);
    // roda depois dos filtros de segurança para mostrar o Authentication já
    // populado
    reg.setOrder(Ordered.LOWEST_PRECEDENCE);
    reg.setName("requestDebugFilter");
    return reg;
  }

}