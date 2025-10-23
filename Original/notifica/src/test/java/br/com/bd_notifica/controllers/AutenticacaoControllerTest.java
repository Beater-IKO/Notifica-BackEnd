package br.com.bd_notifica.controllers;

import br.com.bd_notifica.dto.DadosAutenticacao;
import br.com.bd_notifica.dto.DadosTokenJWT;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.services.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutenticacaoControllerTest {

    @Mock
    private AuthenticationManager manager;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AutenticacaoController controller;

    @Test
    void deveEfetuarLoginERetornarToken() {
        // Arrange (preparação)
        DadosAutenticacao dados = new DadosAutenticacao("email@teste.com", "12345");
        User usuario = new User();
        usuario.setEmail("email@teste.com");

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(usuario);
        when(tokenService.gerarToken(usuario)).thenReturn("token-jwt-falso");

        // Act (ação)
        ResponseEntity resposta = controller.efetuarLogin(dados);

        // Assert (verificação)
        assertNotNull(resposta);
        assertEquals(200, resposta.getStatusCodeValue());

        DadosTokenJWT corpo = (DadosTokenJWT) resposta.getBody();
        assertNotNull(corpo);
        assertEquals("token-jwt-falso", corpo.token());

        // Verifica se as chamadas foram feitas
        verify(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).gerarToken(usuario);
    }
}
