package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Material;

// Repositório para materiais
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    // Busca material por nome
    List<Material> findByNomeIgnoreCase(String nome);
    // Materiais com estoque maior que X
    List<Material> findByQuantidadeEstoqueGreaterThan(Integer quantidade);
    
    // Query customizada para materiais em estoque
    @Query("SELECT m FROM Material m WHERE m.quantidadeEstoque > :minimo")
    List<Material> findMateriaisComEstoque(@Param("minimo") Integer minimo);
}