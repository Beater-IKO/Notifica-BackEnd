package br.com.bd_notifica.services;

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

    
            public Ticket findById(Long id){

               Optional<Ticket> ticket =  this.criacaoDeTicketRepository.findById(id);
               return ticket.get();

        }

}
