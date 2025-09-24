package br.com.bd_notifica.services;

import br.com.bd_notifica.config.GenericExceptions.NotFound;
import br.com.bd_notifica.entities.MensagemRetorno;
import br.com.bd_notifica.repositories.MensagemRetornoRepository;

import java.util.List;
import org.springframework.stereotype.Service;

// Serviço para mensagens de retorno
@Service
public class MensagemRetornoService {

    private final MensagemRetornoRepository mensagemRetornoRepository;

    public MensagemRetornoService(MensagemRetornoRepository mensagemRetornoRepository) {
        this.mensagemRetornoRepository = mensagemRetornoRepository;
    }

    // Salvar mensagem
    public MensagemRetorno save(MensagemRetorno mensagemRetorno) {
        return mensagemRetornoRepository.save(mensagemRetorno);
    }

    // Listar mensagens
    public List<MensagemRetorno> findAll() {
        return mensagemRetornoRepository.findAll();
    }

    // Buscar por ID
    public MensagemRetorno findById(Integer id) {
        return mensagemRetornoRepository.findById(id)
                .orElseThrow(() -> new NotFound("Mensagem não encontrada"));
    }

    // Mensagens de um ticket
    public List<MensagemRetorno> findByTicket(Integer id) {
        return mensagemRetornoRepository.findByTicketId(id);
    }

    // Mensagens de um usuário
    public List<MensagemRetorno> findByUser(String nome) {
        return mensagemRetornoRepository.findByUsuario_NomeIgnoreCase(nome);
    }

    // Excluir mensagem
    public void delete(Integer id) {
        MensagemRetorno mensagemRetorno = findById(id);
        mensagemRetornoRepository.delete(mensagemRetorno);
    }

}
