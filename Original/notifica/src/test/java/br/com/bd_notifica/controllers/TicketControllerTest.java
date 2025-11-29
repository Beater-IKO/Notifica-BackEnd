package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.GrauDePrioridade;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.TicketService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TicketController ticketController;

    private Ticket ticket;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuração de um ticket simulado
        ticket = new Ticket();
        ticket.setId(1);
        ticket.setProblema("Computador não liga");
        ticket.setPrioridade(GrauDePrioridade.URGENTE);
        ticket.setStatus(Status.EM_ANDAMENTO);

        // Configuração de um usuário simulado
        user = new User();
        user.setId(1);
        user.setRole(UserRole.ADMIN);
    }

    @Test
    void deveSalvarTicket() {
        when(ticketService.save(ticket)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.save(ticket);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketService, times(1)).save(ticket);
    }

    @Test
    void deveListarTodosOsTickets() {
        when(authentication.getPrincipal()).thenReturn(user);
        when(ticketService.findAll()).thenReturn(List.of(ticket));

        ResponseEntity<?> response = ticketController.findAll(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((List<?>) response.getBody()).size() > 0);
        verify(ticketService, times(1)).findAll();
    }

    @Test
    void deveBuscarTicketPorId() {
        when(ticketService.findById(1)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketService, times(1)).findById(1);
    }

    @Test
    void deveBuscarTicketsPorCategoria() {
        when(ticketService.findByCategoriaId(2)).thenReturn(List.of(ticket));

        ResponseEntity<List<Ticket>> response = ticketController.findByCategoria(2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ticketService, times(1)).findByCategoriaId(2);
    }

    @Test
    void deveAtualizarTicket() {
        when(ticketService.update(1, ticket)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.update(1, ticket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(ticketService, times(1)).update(1, ticket);
    }

    @Test
    void deveExcluirTicket() {
        doNothing().when(ticketService).delete(1);

        ResponseEntity<Void> response = ticketController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ticketService, times(1)).delete(1);
    }
}
