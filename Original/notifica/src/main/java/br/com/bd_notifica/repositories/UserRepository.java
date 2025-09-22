package br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;

import java.util.List;

// Acesso aos dados de usuários
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Busca por nome sem diferenciar maiúscula/minúscula - query gerada
    // automaticamente
    List<User> findByNomeIgnoreCase(String nome);

    // Filtra usuários por tipo (ADMIN, USER) - query gerada automaticamente
    List<User> findByRole(UserRole role);

    // Busca usuário para autenticação
    User findByUsuarioAndSenha(String usuario, String senha);
    
    // Busca usuário por nome de usuário
    User findByUsuario(String usuario);
    
    // Busca por email
    User findByEmail(String email);
}
