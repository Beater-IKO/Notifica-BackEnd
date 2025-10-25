package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulação de um usuário base
        user = new User();
        user.setId(1);
        user.setNome("Augusto Ferreira");
        user.setCpf("12345678900");
        user.setEmail("augusto@email.com");
        user.setUsuario("augusto123");
        user.setSenha("123456");
        user.setRole(UserRole.ESTUDANTE);
    }

    @Test
    void deveSalvarUsuario() {
        when(userService.save(user)).thenReturn(user);

        ResponseEntity<?> response = userController.save(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).save(user);
    }

    @Test
    void deveListarTodosUsuarios() {
        when(userService.findAll()).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).findAll();
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(userService.findById(1)).thenReturn(user);

        ResponseEntity<User> response = userController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).findById(1);
    }

    @Test
    void deveBuscarUsuarioPorNome() {
        when(userService.findByNome("Augusto")).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userController.findByNome("Augusto");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).findByNome("Augusto");
    }

    @Test
    void deveBuscarUsuarioPorRole() {
        when(userService.findByRole(UserRole.ESTUDANTE)).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userController.findByRole("ESTUDANTE");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).findByRole(UserRole.ESTUDANTE);
    }

    @Test
    void deveAtualizarUsuario() {
        when(userService.update(1, user)).thenReturn(user);

        ResponseEntity<User> response = userController.update(1, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).update(1, user);
    }

    @Test
    void deveExcluirUsuario() {
        doNothing().when(userService).delete(1);

        ResponseEntity<User> response = userController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).delete(1);
    }
}
