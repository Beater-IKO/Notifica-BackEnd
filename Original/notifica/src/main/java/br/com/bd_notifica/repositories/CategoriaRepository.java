package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Categoria;

// Repositório para categorias de tickets
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Busca categoria por nome ignorando maiúscula/minúscula
    List<Categoria> findByNomeIgnoreCase(String nome);
}
