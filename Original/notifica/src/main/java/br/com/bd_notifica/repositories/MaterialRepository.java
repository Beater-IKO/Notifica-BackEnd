package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findByNomeIgnoreCase(String nome);
    List<Material> findByQuantidadeEstoqueGreaterThan(Integer quantidade);
    
    @Query("SELECT m FROM Material m WHERE m.quantidadeEstoque > :minimo")
    List<Material> findMateriaisComEstoque(@Param("minimo") Integer minimo);
}