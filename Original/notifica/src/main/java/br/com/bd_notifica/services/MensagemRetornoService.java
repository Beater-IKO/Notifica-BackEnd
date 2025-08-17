package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.MensagemRetorno;
import br.com.bd_notifica.repositories.MensagemRetornoRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MensagemRetornoService {
    
    private final MensagemRetornoRepository mensagemRetornoRepository;

    public MensagemRetornoService(MensagemRetornoRepository mensagemRetornoRepository){
            this.mensagemRetornoRepository = mensagemRetornoRepository;
    }
    
    public MensagemRetorno save(MensagemRetorno mensagemRetorno){
        return mensagemRetornoRepository.save(mensagemRetorno);
    }

    public List<MensagemRetorno> findAll(){
        return mensagemRetornoRepository.findAll();
    }

    public MensagemRetorno findById(Integer id){
        return mensagemRetornoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
    }

    public List<MensagemRetorno> findByTicket(Integer id){
        return mensagemRetornoRepository.findByTicketId(id);
    }

    public List<MensagemRetorno> findByUser(String nome){
        return mensagemRetornoRepository.findByUsuario_NomeIgnoreCase(nome);
    }

    public void delete(Integer id){
        MensagemRetorno mensagemRetorno = findById(id);
        mensagemRetornoRepository.delete(mensagemRetorno);
    }

}
