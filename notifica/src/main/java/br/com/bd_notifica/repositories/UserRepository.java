package br.com.bd_notifica.repositories;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.bd_notifica.configs.CustomFactory;
import br.com.bd_notifica.entities.UserEntity;

public class UserRepository {

    public UserEntity createUser(UserEntity user) {
        EntityManager em = CustomFactory.getEntityManager(); // Obtém o EntityManager aqui
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            System.out.println("Usuário criado com sucesso!: " + user.getName());
            return user;
        } catch (Exception e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close(); // Garante que o EntityManager seja fechado
        }
    }

    public static List<UserEntity> listarTodos() {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity", UserEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public UserEntity findByEmail(String email) {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário por email: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public UserEntity findByName(String name) {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.name = :name", UserEntity.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se nenhum usuário for encontrado
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário por nome: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public UserEntity findById(Long id) {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            return em.find(UserEntity.class, id);
        } finally {
            em.close();
        }
    }

    public UserEntity findByCreateOnDate(LocalDate createOnDate) {
        EntityManager em = CustomFactory.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM UserEntity u WHERE u.createOnDate = :createOnDate", UserEntity.class)
                    .setParameter("createOnDate", createOnDate)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário por data de criação: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }
}