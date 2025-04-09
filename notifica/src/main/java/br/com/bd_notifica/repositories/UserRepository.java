package br.com.bd_notifica.repositories;

import javax.persistence.EntityManager;

import br.com.bd_notifica.configs.CustomFactory;
import br.com.bd_notifica.entities.UserEntity;

public class UserRepository {

    EntityManager em = CustomFactory.getEntityManager();

    public UserEntity createUser(UserEntity user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }


    public UserEntity findByEmailAndPassword(String email, String password) {
        try {
            return em
                    .createQuery("SELECT u FROM users u WHERE u.email = :email AND u.password = :password",
                            UserEntity.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserEntity findByName(String name){
        return em.createQuery("SELECT u FROM users u WHERE u.name = :name", UserEntity.class)
        .setParameter(name, name)
        .getSingleResult();
    }

    public UserEntity findById(Long id){
        return em.find(UserEntity.class, id);
    }

    public UserEntity findByCreateOnDate(String createOnDate){
        return em.createQuery("SELECT u FROM users u WHERE u.createOnDate = :createOnDate", UserEntity.class)
                .setParameter("createOnDate", createOnDate)
                .getSingleResult();
    }

}
