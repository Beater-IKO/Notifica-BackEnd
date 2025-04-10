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

    // Atualizar um ticket
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

// Buscar por ID do aluno
public List<Ticket> buscarPorAlunoId(Long alunoId) {
    EntityManager em = CustomFactory.getEntityManager();
    TypedQuery<Ticket> query = em.createQuery(
        "SELECT t FROM Ticket t WHERE t.alunoId = :alunoId", Ticket.class);
    query.setParameter("alunoId", alunoId);
    return query.getResultList();
}

// Buscar por intervalo de datas
public List<Ticket> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
    EntityManager em = CustomFactory.getEntityManager();
    TypedQuery<Ticket> query = em.createQuery(
        "SELECT t FROM Ticket t WHERE t.dataCriacao BETWEEN :inicio AND :fim", Ticket.class);
    query.setParameter("inicio", inicio);
    query.setParameter("fim", fim);
    return query.getResultList();
}

}
