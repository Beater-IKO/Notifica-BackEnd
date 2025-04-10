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

    public UserEntity findByEmailAndPassword(String email, String password) {
        try {
            UserEntity user = 
            em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email AND u.password = :password",
                            UserEntity.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

                    System.out.println("Usuário existente, conta logada com sucesso!: " + user.getEmail());
                    return user;
        } catch (Exception e) {
            System.out.println("Usuário não encontrado, verifique o email e a senha!");
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
