package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;

// Acesso aos dados de protocolos
@Repository
public interface ProtocoloRepository extends JpaRepository<Protocolo, Integer> {
    // Filtra por status da requisição
    List<Protocolo> findByStatus(StatusProtocolo status);
    // Busca requisições de um usuário
    List<Protocolo> findByUserId(Integer userId);
}