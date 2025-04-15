package br.com.bd_notifica.repositories;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.bd_notifica.configs.CustomFactory;
import br.com.bd_notifica.entities.UserEntity;

public class UserRepository {

    EntityManager em = CustomFactory.getEntityManager();

    public UserEntity createUser(UserEntity user) {
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            System.out.println("Usuário criado com sucesso!: " + user.getName());
            return user;
        } catch (Exception e) {
            System.out.println("Erro ao criar usuário: " + e.getMessage());
            em.getTransaction().rollback();
            return null;
        }
    }

    public UserEntity findByEmail(String email) {
        try {
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", email)
                    .getSingleResult();

            System.out.println("Usuário encontrado: " + user.getEmail());
            return user;
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuário por email: " + e.getMessage());
            return null;
        }
    }

    public UserEntity findByName(String name) {

        return em.createQuery("SELECT u FROM UserEntity u WHERE u.name = :name", UserEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public UserEntity findById(Long id) {
        return em.find(UserEntity.class, id);
    }

    public UserEntity findByCreateOnDate(LocalDate createOnDate) {
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.createOnDate = :createOnDate", UserEntity.class)
                .setParameter("createOnDate", createOnDate)
                .getSingleResult();
    }

}
