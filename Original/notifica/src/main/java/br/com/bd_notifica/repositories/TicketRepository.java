package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Status;

// Acesso aos dados de tickets
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Busca tickets por categoria
    List<Ticket> findByCategoriaId(Integer categoriaId);

    // Busca tickets por usuário
    List<Ticket> findByUserId(Integer userId);

    // Busca tickets por status
    List<Ticket> findByStatus(Status status);

    // Busca tickets por usuário e status
    List<Ticket> findByUserIdAndStatus(Integer userId, Status status);

}
