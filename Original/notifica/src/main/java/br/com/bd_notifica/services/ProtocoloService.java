package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;
import br.com.bd_notifica.repositories.ProtocoloRepository;

import java.util.List;

@Service
public class ProtocoloService {
    private final ProtocoloRepository protocoloRepository;

    public ProtocoloService(ProtocoloRepository protocoloRepository){
        this.protocoloRepository = protocoloRepository;
    }

    public Protocolo save(Protocolo protocolo){
        // Regra de negócio: modificar objeto antes de persistir
        if(protocolo.getObservacoes() == null || protocolo.getObservacoes().isBlank()){
            protocolo.setStatus(StatusProtocolo.PENDENTE);
        } else {
            protocolo.setStatus(StatusProtocolo.EM_ANALISE);
        }

        // Regra de negócio: exception para validação complexa
        if(protocolo.getMaterial() == null){
            throw new RuntimeException("Não é possível criar protocolo sem material associado");
        }

        return protocoloRepository.save(protocolo);
    }

    public List<Protocolo> findAll(){
        return protocoloRepository.findAll();
    }

    public Protocolo findById(Integer id){
        return protocoloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Protocolo não encontrado"));
    }

    public List<Protocolo> findByStatus(StatusProtocolo status){
        return protocoloRepository.findByStatus(status);
    }

    public List<Protocolo> findByUserId(Integer userId){
        return protocoloRepository.findByUserId(userId);
    }

    public Protocolo update(Integer id, Protocolo protocolo){
        Protocolo update = findById(id);

        if(protocolo.getDescricao() != null && !protocolo.getDescricao().isBlank()){
            update.setDescricao(protocolo.getDescricao());
        }

        if(protocolo.getQuantidadeSolicitada() != null){
            update.setQuantidadeSolicitada(protocolo.getQuantidadeSolicitada());
        }

        if(protocolo.getObservacoes() != null){
            update.setObservacoes(protocolo.getObservacoes());
        }

        if(protocolo.getStatus() != null){
            update.setStatus(protocolo.getStatus());
        }

        if(protocolo.getMaterial() != null){
            update.setMaterial(protocolo.getMaterial());
        }

        return protocoloRepository.save(update);
    }

    public void delete(Integer id){
        Protocolo delete = findById(id);
        protocoloRepository.delete(delete);
    }
}