package br.com.bd_notifica.config;

import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityTokenFilter.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        String method = request.getMethod();
        logger.info("=== SecurityTokenFilter - Processando: {} {} ===", method, uri);
        
        // Pular requisições OPTIONS (CORS preflight)
        if ("OPTIONS".equals(method)) {
            logger.info("Requisição OPTIONS detectada, pulando validação de token");
            filterChain.doFilter(request, response);
            return;
        }
        
        // Pular rotas públicas
        if (uri.startsWith("/api/auth/") || uri.contains("/api/tickets/public-test") || uri.contains("/api/debug/") || uri.contains("/api/admin/")) {
            logger.info("Rota pública detectada, pulando validação de token");
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = recuperarToken(request);
        logger.info("Token encontrado: {}", token != null ? "SIM (" + token.substring(0, Math.min(20, token.length())) + "...)" : "NÃO");
        
        if (token != null) {
            String subject = tokenService.validateToken(token);
            logger.info("Token válido? Subject: {}", subject);
            
            if (subject != null) {
                var userOptional = userRepository.findByEmail(subject);
                logger.info("Usuário encontrado no banco: {}", userOptional.isPresent());
                
                if (userOptional.isPresent()) {
                    UserDetails user = userOptional.get();
                    logger.info("Usuário: {} | Role: {} | Authorities: {}", 
                        user.getUsername(), 
                        ((br.com.bd_notifica.entities.User) user).getRole(),
                        user.getAuthorities());
                    
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication definido com sucesso!");
                } else {
                    logger.warn("Usuário não encontrado no banco para email: {}", subject);
                }
            } else {
                logger.warn("Token inválido ou expirado");
            }
        } else {
            logger.info("Nenhum token JWT encontrado no header Authorization");
        }
        
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization header completo: {}", authorizationHeader != null ? authorizationHeader : "[AUSENTE]");
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            logger.info("Token extraído: {}...", token.substring(0, Math.min(20, token.length())));
            return token;
        }
        
        logger.warn("Header Authorization não contém Bearer token válido");
        return null;
    }
}