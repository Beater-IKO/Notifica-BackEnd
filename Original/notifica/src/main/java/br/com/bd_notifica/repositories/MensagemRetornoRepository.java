package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.MensagemRetorno;

// Repositório para mensagens de retorno
@Repository
public interface MensagemRetornoRepository extends JpaRepository <MensagemRetorno, Integer>{
    // Busca mensagens por nome do usuário
    List<MensagemRetorno> findByUsuario_NomeIgnoreCase(String nome);
    // Mensagens de um ticket específico
    List<MensagemRetorno> findByTicketId(Integer id);
}
