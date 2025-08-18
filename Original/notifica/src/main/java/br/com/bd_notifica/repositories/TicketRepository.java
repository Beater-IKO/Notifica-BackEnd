package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bd_notifica.entities.Ticket;

// Acesso aos dados de tickets
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Busca tickets por categoria
    List<Ticket> findByCategoriaId(Integer categoriaId);

}
