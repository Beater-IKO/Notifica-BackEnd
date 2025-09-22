package br.com.bd_notifica.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bd_notifica.config.RecursoNaoEncontradoException;
import br.com.bd_notifica.config.RegraDeNegocioException;
import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.enums.Status;

// Regras de negócio para tickets
@Service
public class TicketService {

    // repositórios para acessar dados do banco
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket save(Ticket ticket) {
        // Validação de campos obrigatórios
        if (ticket.getProblema() == null || ticket.getProblema().isBlank()) {
            throw new ValidationException("Problema é obrigatório");
        }



        // Define status inicial automaticamente
        if (ticket.getStatus() == null) {
            ticket.setStatus(Status.VISTO);
        }

        // Define timestamps
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    // Lista todos os tickets
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    // Busca ticket por ID
    public Ticket findById(Integer id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ticket não encontrado"));
    }

    // Filtra tickets por categoria
    public List<Ticket> findByCategoriaId(Integer categoriaId) {
        return ticketRepository.findByCategoriaId(categoriaId);
    }

    public Ticket update(Integer id, Ticket ticket) {
        Ticket existingTicket = findById(id);

        // Não permite modificar tickets finalizados
        if (existingTicket.getStatus() == Status.FINALIZADO) {
            throw new RegraDeNegocioException("Não é possível modificar ticket finalizado");
        }

        if (ticket.getProblema() != null && ticket.getProblema().isBlank()) {
            throw new ValidationException("O campo 'problema' foi enviado mas está vazio");
        }

        if (ticket.getProblema() != null) {
            existingTicket.setProblema(ticket.getProblema());
        }

        if (ticket.getArea() != null) {
            existingTicket.setArea(ticket.getArea());
        }

        if (ticket.getPrioridade() != null) {
            existingTicket.setPrioridade(ticket.getPrioridade());
        }

        if (ticket.getStatus() != null) {
            existingTicket.setStatus(ticket.getStatus());
        }

        if (ticket.getUser() != null) {
            existingTicket.setUser(ticket.getUser());
        }

        return ticketRepository.save(existingTicket);
    }

    public void delete(Integer id) {
        Ticket ticketToDelete = findById(id);

        // Apenas tickets vistos podem ser excluídos
        if (ticketToDelete.getStatus() != Status.VISTO) {
            throw new RegraDeNegocioException("Apenas tickets vistos podem ser excluídos");
        }

        ticketRepository.delete(ticketToDelete);
    }

    // filtra tickets por critérios específicos
    public List<Ticket> filterTickets(String priority, String area, String status, Integer assignedTo) {
        List<Ticket> tickets = ticketRepository.findAll();
        
        return tickets.stream()
            .filter(t -> priority == null || t.getPrioridade().toString().equals(priority))
            .filter(t -> area == null || t.getArea().toString().equals(area))
            .filter(t -> status == null || t.getStatus().toString().equals(status))
            .filter(t -> assignedTo == null || (t.getAssignedTo() != null && t.getAssignedTo().getId().equals(assignedTo)))
            .collect(Collectors.toList());
    }

    // atribui um ticket para um usuário específico
    public Ticket assignTicket(Integer ticketId, Integer userId) {
        Ticket ticket = findById(ticketId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        ticket.setAssignedTo(user);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    // atualiza o status de um ticket
    public Ticket updateStatus(Integer ticketId, String statusStr) {
        Ticket ticket = findById(ticketId);
        Status status = Status.valueOf(statusStr);
        
        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    // gera estatísticas dos tickets
    public Map<String, Object> getStatistics() {
        List<Ticket> allTickets = ticketRepository.findAll();
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("total", allTickets.size());
        stats.put("byStatus", allTickets.stream()
            .collect(Collectors.groupingBy(t -> t.getStatus().toString(), Collectors.counting())));
        stats.put("byPriority", allTickets.stream()
            .collect(Collectors.groupingBy(t -> t.getPrioridade().toString(), Collectors.counting())));
        stats.put("byArea", allTickets.stream()
            .collect(Collectors.groupingBy(t -> t.getArea().toString(), Collectors.counting())));
        
        return stats;
    }

    // busca tickets com paginação
    public Page<Ticket> findAllPaginated(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }
}
