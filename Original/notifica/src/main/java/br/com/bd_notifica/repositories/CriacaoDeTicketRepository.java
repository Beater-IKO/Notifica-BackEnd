package main.java.br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.NotificaCRUD.NotificaCrud.model.Ticket;

public interface CriacaoDeTicketRepository extends JpaRepository<Ticket, Long> {

}
