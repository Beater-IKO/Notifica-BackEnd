package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.config.RegraDeNegocioException;
import br.com.bd_notifica.config.RecursoNaoEncontradoException;

import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;
import br.com.bd_notifica.repositories.ProtocoloRepository;

import java.util.List;

/**
 * Serviço de negócio para sistema de requisição de materiais
 * Implementa regras complexas de aprovação e controle de estoque
 * Gerencia o fluxo completo desde solicitação até entrega dos materiais
 */
@Service
public class ProtocoloService {
    // Repositório para persistência de protocolos de requisição
    private final ProtocoloRepository protocoloRepository;

    public ProtocoloService(ProtocoloRepository protocoloRepository) {
        this.protocoloRepository = protocoloRepository;
    }

    public Protocolo save(Protocolo protocolo) {
        // Regra de negócio: modificar objeto antes de persistir
        if (protocolo.getObservacoes() == null || protocolo.getObservacoes().isBlank()) {
            protocolo.setStatus(StatusProtocolo.PENDENTE);
        } else {
            protocolo.setStatus(StatusProtocolo.EM_ANALISE);
        }

        // Regra de negócio: exception para validação complexa
        if (protocolo.getMaterial() == null) {
            throw new RegraDeNegocioException("Não é possível criar protocolo sem material associado");
        }

        return protocoloRepository.save(protocolo);
    }

    /**
     * Lista todos os protocolos para gestão administrativa
     * Utilizado para controle de estoque e aprovações pendentes
     */
    public List<Protocolo> findAll() {
        return protocoloRepository.findAll();
    }

    /**
     * Busca protocolo específico com tratamento de erro
     * Essencial para acompanhamento de solicitações
     */
    public Protocolo findById(Integer id) {
        return protocoloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Protocolo não encontrado"));
    }

    /**
     * Filtra protocolos por status usando query method
     * Permite gestão de filas de aprovação e acompanhamento
     */
    public List<Protocolo> findByStatus(StatusProtocolo status) {
        return protocoloRepository.findByStatus(status);
    }

    /**
     * Lista protocolos de um usuário específico
     * Permite ao usuário acompanhar suas próprias solicitações
     */
    public List<Protocolo> findByUserId(Integer userId) {
        return protocoloRepository.findByUserId(userId);
    }

    public Protocolo update(Integer id, Protocolo protocolo) {
        Protocolo existingProtocolo = findById(id);

        if (protocolo.getDescricao() != null && !protocolo.getDescricao().isBlank()) {
            existingProtocolo.setDescricao(protocolo.getDescricao());
        }

        if (protocolo.getQuantidadeSolicitada() != null) {
            existingProtocolo.setQuantidadeSolicitada(protocolo.getQuantidadeSolicitada());
        }

        if (protocolo.getObservacoes() != null) {
            existingProtocolo.setObservacoes(protocolo.getObservacoes());
        }

        if (protocolo.getStatus() != null) {
            existingProtocolo.setStatus(protocolo.getStatus());
        }

        if (protocolo.getMaterial() != null) {
            existingProtocolo.setMaterial(protocolo.getMaterial());
        }

        return protocoloRepository.save(existingProtocolo);
    }

    public void delete(Integer id) {
        Protocolo protocoloToDelete = findById(id);
        protocoloRepository.delete(protocoloToDelete);
    }
}