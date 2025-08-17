package br.com.bd_notifica.services;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import br.com.bd_notifica.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.repositories.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }
    
    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public Ticket findById(Integer id){
        return ticketRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
    }

    public Ticket update(Integer id, Ticket ticket){
        Ticket update = findById(id);

        if(ticket.getProblema() != null && !ticket.getProblema().isBlank()){
            update.setProblema(ticket.getProblema());
        }

        if(ticket.getArea() != null){
            update.setArea(ticket.getArea());
        }

        if(ticket.getPrioridade() != null){
            update.setPrioridade(ticket.getPrioridade());
        }

        if(ticket.getStatus() != null){
            update.setStatus(ticket.getStatus());
        }

        return ticketRepository.save(update);
    }

    public void delete(Integer id){
        Ticket delete = findById(id);
        ticketRepository.delete(delete);
    }
}
