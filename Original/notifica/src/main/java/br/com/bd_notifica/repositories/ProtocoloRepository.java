package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;

@Repository
public interface ProtocoloRepository extends JpaRepository<Protocolo, Integer> {
    List<Protocolo> findByStatus(StatusProtocolo status);
    List<Protocolo> findByUserId(Integer userId);
}