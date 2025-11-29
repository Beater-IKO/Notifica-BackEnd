package br.com.bd_notifica.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.config.GenericExceptions.Unauthorized;
import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.GrauDePrioridade;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.services.TicketService;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket testTicket;

    @BeforeEach
    void setUp() {
        testTicket = new Ticket();
        testTicket.setId(1);
        testTicket.setProblema("Problema de teste");
        testTicket.setStatus(Status.VISTO);
        testTicket.setPrioridade(GrauDePrioridade.MEDIA);
    }

    @Test
    void testSaveValidTicket() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        Ticket savedTicket = ticketService.save(testTicket);

        assertNotNull(savedTicket);
        assertEquals(testTicket.getId(), savedTicket.getId());
        assertEquals(testTicket.getProblema(), savedTicket.getProblema());
        assertEquals(testTicket.getStatus(), savedTicket.getStatus());
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void testSaveTicketWithoutProblema() {
        testTicket.setProblema(null);
        assertThrows(ValidationException.class, () -> ticketService.save(testTicket));
    }

    @Test
    void testFindByIdSuccess() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));

        Ticket foundTicket = ticketService.findById(1);

        assertNotNull(foundTicket);
        assertEquals(testTicket.getId(), foundTicket.getId());
    }

    @Test
    void testFindByIdNotFound() {
        when(ticketRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFound.class, () -> ticketService.findById(1));
    }

    @Test
    void testFindAll() {
        List<Ticket> ticketList = Arrays.asList(testTicket);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> result = ticketService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateFinalizedTicket() {
        testTicket.setStatus(Status.FINALIZADOS);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));

        Ticket updatedTicket = new Ticket();
        updatedTicket.setProblema("Novo problema");

        assertThrows(Unauthorized.class, () -> ticketService.update(1, updatedTicket));
    }

    @Test
    void testDeleteNonVistoTicket() {
        testTicket.setStatus(Status.EM_ANDAMENTO);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));

        assertThrows(Unauthorized.class, () -> ticketService.delete(1));
    }

    @Test
    void testFindByCategoriaId() {
        List<Ticket> ticketList = Arrays.asList(testTicket);
        when(ticketRepository.findByCategoriaId(1)).thenReturn(ticketList);

        List<Ticket> result = ticketService.findByCategoriaId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testSaveTicketWithBlankProblema() {
        testTicket.setProblema("");
        assertThrows(ValidationException.class, () -> ticketService.save(testTicket));
    }

    @Test
    void testSaveTicketWithNullStatus() {
        testTicket.setStatus(null);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        Ticket savedTicket = ticketService.save(testTicket);

        assertEquals(Status.VISTO, savedTicket.getStatus());
    }

    @Test
    void testUpdateTicketSuccess() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setProblema("Problema atualizado");

        Ticket result = ticketService.update(1, updatedTicket);

        assertNotNull(result);
        assertEquals("Problema atualizado", result.getProblema());
    }

    @Test
    void testUpdateTicketWithBlankProblema() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));

        Ticket updatedTicket = new Ticket();
        updatedTicket.setProblema("");

        assertThrows(ValidationException.class, () -> ticketService.update(1, updatedTicket));
    }

    @Test
    void testUpdateTicketWithAllFields() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setProblema("Novo problema");
        updatedTicket.setStatus(Status.EM_ANDAMENTO);

        Ticket result = ticketService.update(1, updatedTicket);

        assertNotNull(result);
    }

    @Test
    void testUpdateTicketToFinalized() {
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setStatus(Status.FINALIZADOS);

        Ticket result = ticketService.update(1, updatedTicket);

        assertEquals(Status.FINALIZADOS, result.getStatus());
    }

    @Test
    void testDeleteVistoTicket() {
        testTicket.setStatus(Status.VISTO);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(testTicket));

        assertDoesNotThrow(() -> ticketService.delete(1));
        verify(ticketRepository).delete(testTicket);
    }

}
