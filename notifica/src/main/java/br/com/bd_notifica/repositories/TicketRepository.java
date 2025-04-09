package br.com.bd_notifica.repositories;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.utils.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TicketRepository {

    public Ticket salvar(Ticket ticket) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
            return ticket;
        } finally {
            em.close();
        }
    }

    public List<Ticket> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery("FROM Ticket", Ticket.class);
        return query.getResultList();
    }

    public Ticket buscarPorId(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        return em.find(Ticket.class, id);
    }

    public void deletar(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Ticket ticket = em.find(Ticket.class, id);
            if (ticket != null) {
                em.getTransaction().begin();
                em.remove(ticket);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
