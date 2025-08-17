package br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bd_notifica.entities.Ticket;

public interface CriacaoDeTicketRepository extends JpaRepository<Ticket, Integer> {

}
