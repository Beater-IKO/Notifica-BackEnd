package br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Sala;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {
}
