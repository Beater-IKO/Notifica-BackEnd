package br.com.bd_notifica.repositories;

import br.com.bd_notifica.configs.CustomFactory;
import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.UserEntity;
import br.com.bd_notifica.enums.UserRole;

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


    public List<Ticket> buscarPorUsuario(UserEntity user){
        EntityManager em = CustomFactory.getEntityManager();
        try{
            return em.createQuery("SELECT t FROM Ticket t WHERE t.user = :user", Ticket.class)
                    .setParameter("user", user)
                    .getResultList();
        }finally{
            em.close();
        }
    }

    public Ticket buscarPorId(Long id) {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
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

    public List<Ticket> buscarPorAlunoId(UserEntity user) {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery(
                "SELECT t FROM Ticket t WHERE t.user = :user", Ticket.class);
        query.setParameter("user", user);
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

    public List<Object[]> contarChamadosPorStatus() {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Object[]> query = em.createQuery(
            "SELECT t.status, COUNT(t) FROM Ticket t GROUP BY t.status",
            Object[].class
        );
        return query.getResultList();
    }

    public List<Ticket> buscarChamadosPorTipoUsuario(UserRole role) {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t JOIN t.user u WHERE u.role = :role", Ticket.class
        );
        query.setParameter("role", role);
        return query.getResultList();
    }

    public List<Ticket> buscarPorNomeUsuario(String nomeParcial) {
        EntityManager em = CustomFactory.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery(
            "SELECT t FROM Ticket t JOIN t.user u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))",
            Ticket.class
        );
        query.setParameter("name", nomeParcial);
        return query.getResultList();
    }
}
