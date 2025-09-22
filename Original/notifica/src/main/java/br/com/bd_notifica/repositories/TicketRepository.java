package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.enums.Area;
import br.com.bd_notifica.enums.GrauDePrioridade;

// Acesso aos dados de tickets
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Busca tickets por categoria
    List<Ticket> findByCategoriaId(Integer categoriaId);

    // Busca tickets por status
    List<Ticket> findByStatus(Status status);

    // Busca tickets por área
    List<Ticket> findByArea(Area area);

    // Busca tickets por prioridade
    List<Ticket> findByPrioridade(GrauDePrioridade prioridade);

    // Busca tickets atribuídos a um usuário
    List<Ticket> findByAssignedToId(Integer userId);

    // Busca tickets criados por um usuário
    List<Ticket> findByUserId(Integer userId);

    // Query customizada para filtros avançados
    @Query("SELECT t FROM Ticket t WHERE " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:area IS NULL OR t.area = :area) AND " +
           "(:prioridade IS NULL OR t.prioridade = :prioridade) AND " +
           "(:assignedToId IS NULL OR t.assignedTo.id = :assignedToId)")
    List<Ticket> findByFilters(@Param("status") Status status,
                              @Param("area") Area area,
                              @Param("prioridade") GrauDePrioridade prioridade,
                              @Param("assignedToId") Integer assignedToId);

}
