package br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE UPPER(u.nome) = UPPER(:nome)")
    List<User> findByNomeIgnoreCase(@Param("nome") String nome);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") UserRole role);
}
