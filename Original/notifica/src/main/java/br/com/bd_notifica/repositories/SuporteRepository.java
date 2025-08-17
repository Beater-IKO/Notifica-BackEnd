package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;

@Repository
public interface SuporteRepository extends JpaRepository<Suporte, Integer> {
    List<Suporte> findByTipo(TipoSuporte tipo);
    List<Suporte> findByStatus(Status status);
}