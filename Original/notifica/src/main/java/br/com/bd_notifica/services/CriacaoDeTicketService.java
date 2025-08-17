package br.com.bd_notifica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.repositories.CriacaoDeTicketRepository;

@Service
public class CriacaoDeTicketService {

        @Autowired
        private CriacaoDeTicketRepository criacaoDeTicketRepository;

        public String save(Ticket ticket){
           
            this.criacaoDeTicketRepository.save(ticket);

            return "Ticket aslvo com sucesso";
        }

        public List<Ticket> findAll() {
        return this.criacaoDeTicketRepository.findAll();
        }
    
            public Ticket findById(Integer id){

               Optional<Ticket> ticket =  this.criacaoDeTicketRepository.findById(id);
               return ticket.get();

        }

         public String update(Integer id, Ticket ticketAtualizado) {
        Optional<Ticket> ticketExistente = this.criacaoDeTicketRepository.findById(id);
        
        if (ticketExistente.isPresent()) {
            Ticket ticket = ticketExistente.get();
            ticket.setProblema(ticketAtualizado.getProblema());
            ticket.setPrioridade(ticketAtualizado.getPrioridade());
            ticket.setArea(ticketAtualizado.getArea());
            ticket.setStatus(ticketAtualizado.getStatus());
            
            this.criacaoDeTicketRepository.save(ticket);
            return "Ticket atualizado com sucesso";
        }
        return "Ticket não encontrado";
    }

    public String delete(Integer id) {
        if (this.criacaoDeTicketRepository.existsById(id)) {
            this.criacaoDeTicketRepository.deleteById(id);
            return "Ticket deletado com sucesso";
        }
        return "Ticket não encontrado";
    }

}
