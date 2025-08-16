package br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNomeIgnoreCase(String nome);
    List<User> findByRole(UserRole role);
}
