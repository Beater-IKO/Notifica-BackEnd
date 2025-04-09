package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.repositories.TicketRepository;

import java.time.LocalDate;
import java.util.List;

public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket criarTicket(Ticket ticket) {
        ticket.setDataCriacao(LocalDate.now());
        return ticketRepository.salvar(ticket);
    }

    public List<Ticket> listarTodos() {
        return ticketRepository.listarTodos();
    }

    public Ticket buscarPorId(Long id) {
        return ticketRepository.buscarPorId(id);
    }

    public void deletar(Long id) {
        ticketRepository.deletar(id);
    }
}
