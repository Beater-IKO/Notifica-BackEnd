package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.enums.Andar;

// Repositório para salas
@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {
    // Busca sala por número
    List<Sala> findByNumeroIgnoreCase(String numero);
    // Salas de um andar específico
    List<Sala> findByAndar(Andar andar);
}
