package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.enums.Andar;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {
    List<Sala> findByNumeroIgnoreCase(String numero);
    List<Sala> findByAndar(Andar andar);
}
