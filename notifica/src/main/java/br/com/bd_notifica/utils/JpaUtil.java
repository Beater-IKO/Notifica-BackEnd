package br.com.bd_notifica.utils;


import javax.persistence.*;

public class JpaUtil {



private static final EntityManagerFactory emf=
                Persistence.createEntityManagerFactory("ticketPU");

                public static EntityManager getEntityManager(){
                    return emf.createEntityManager();
                }



}
