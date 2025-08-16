package br.com.bd_notifica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bd_notifica.entities.MensagemRetorno;

@Repository
public interface MensagemRetornoRepository extends JpaRepository <MensagemRetorno, Integer>{
}
