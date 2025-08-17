package br.com.bd_notifica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.MensagemRetorno;

@Repository
public interface MensagemRetornoRepository extends JpaRepository <MensagemRetorno, Integer>{
    List<MensagemRetorno> findByUserIgnoreCase(String user);
    List<MensagemRetorno> findByTicketIgnoreCase(String ticket);
}
