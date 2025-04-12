package br.com.bd_notifica.repositories;

import br.com.bd_notifica.configs.CustomFactory;
import br.com.bd_notifica.entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class TicketRepository {

    public Ticket salvar(Ticket ticket) {
        EntityManager em = CustomFactory.getEntityManager();
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
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery("FROM Ticket", Ticket.class);
        return query.getResultList();
    }

    public Ticket buscarPorId(Long id) {
        EntityManager em = CustomFactory.getEntityManager();
        return em.find(Ticket.class, id);
    }

    public void deletar(Long id) {
        EntityManager em = CustomFactory.getEntityManager();
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

    public Ticket editar(Ticket ticket) {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            ticket = em.merge(ticket);
            em.getTransaction().commit();
            return ticket;
        } finally {
            em.close();
        }
    }

    public List<Ticket> buscarPorAlunoId(Long alunoId) {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.alunoId = :alunoId", Ticket.class);
        query.setParameter("alunoId", alunoId);
        return query.getResultList();
    }

    public List<Ticket> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.dataCriacao BETWEEN :inicio AND :fim", Ticket.class);
        query.setParameter("inicio", inicio);
        query.setParameter("fim", fim);
        return query.getResultList();
    }

    public List<Ticket> buscarPorStatus(String status) {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.status = :status", Ticket.class);
        query.setParameter("status", status);
        return query.getResultList();
    }
}
