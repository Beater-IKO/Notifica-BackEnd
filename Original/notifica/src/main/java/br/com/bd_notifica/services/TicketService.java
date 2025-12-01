package br.com.bd_notifica.services;

import java.util.List;
import org.springframework.stereotype.Service;

import br.com.bd_notifica.config.ValidationException;
import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.config.GenericExceptions.Unauthorized;
import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.enums.Status;

// Regras de negócio para tickets
@Service
public class TicketService {

    // Acesso ao banco de dados
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(Ticket ticket) {
        // Validação de campos obrigatórios

        if (ticket.getProblema() == null || ticket.getProblema().isBlank()) {
            throw new ValidationException("Problema é obrigatório");
        }

        if (ticket.getPrioridade() == null) {
            throw new ValidationException("Prioridade é obrigatória");
        }

        if (ticket.getDescricao() == null || ticket.getDescricao().isBlank()) {
            throw new ValidationException("Descrição é obrigatória");
        }

        if (ticket.getSala() == null) {
            throw new ValidationException("Sala é obrigatória");
        }

        // Define status inicial automaticamente
        if (ticket.getStatus() == null) {
            ticket.setStatus(Status.VISTO);
        }

        return ticketRepository.save(ticket);
    }

    // Lista todos os tickets
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    // Busca ticket por ID
    public Ticket findById(Integer id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new NotFound("Ticket não encontrado"));
    }

    // Filtra tickets por categoria
    public List<Ticket> findByCategoriaId(Integer categoriaId) {
        return ticketRepository.findByCategoriaId(categoriaId);
    }

    // Busca tickets por usuário
    public List<Ticket> findByUserId(Integer userId) {
        return ticketRepository.findByUserId(userId);
    }

    // Busca tickets por status
    public List<Ticket> findByStatus(Status status) {
        return ticketRepository.findByStatus(status);
    }

    // Busca tickets por usuário e status
    public List<Ticket> findByUserIdAndStatus(Integer userId, Status status) {
        return ticketRepository.findByUserIdAndStatus(userId, status);
    }

    // Atualizar apenas o status do ticket
    public Ticket updateStatus(Integer id, Status novoStatus) {
        Ticket ticket = findById(id);
        
        // Validações de negócio
        if (ticket.getStatus() == Status.FINALIZADOS) {
            throw new Unauthorized("Não é possível alterar status de ticket finalizado");
        }
        
        ticket.setStatus(novoStatus);
        return ticketRepository.save(ticket);
    }

    public Ticket update(Integer id, Ticket ticket) {
        Ticket existingTicket = findById(id);

        // Não permite modificar tickets finalizados
        if (existingTicket.getStatus() == Status.FINALIZADOS) {
            throw new Unauthorized("Não é possível modificar ticket finalizado");
        }

        if (ticket.getProblema() != null && ticket.getProblema().isBlank()) {
            throw new ValidationException("O campo 'problema' foi enviado mas está vazio");
        }

        if (ticket.getProblema() != null) {
            existingTicket.setProblema(ticket.getProblema());
        }

        if (ticket.getPrioridade() != null) {
            existingTicket.setPrioridade(ticket.getPrioridade());
        }

        if (ticket.getStatus() != null) {
            if (ticket.getStatus() == Status.FINALIZADOS) {
                // Ticket pode ser finalizado
            }
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
            throw new Unauthorized("Apenas tickets vistos podem ser excluídos");
        }

        ticketRepository.delete(ticketToDelete);
    }
}
