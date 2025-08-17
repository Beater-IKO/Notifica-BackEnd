package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.repositories.SuporteRepository;

import java.util.List;

@Service
public class SuporteService {
    private final SuporteRepository suporteRepository;

    public SuporteService(SuporteRepository suporteRepository){
        this.suporteRepository = suporteRepository;
    }

    public Suporte save(Suporte suporte){
        return suporteRepository.save(suporte);
    }

    public List<Suporte> findAll(){
        return suporteRepository.findAll();
    }

    public Suporte findById(Integer id){
        return suporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suporte não encontrado"));
    }

    public List<Suporte> findByTipo(TipoSuporte tipo){
        return suporteRepository.findByTipo(tipo);
    }

    public List<Suporte> findByStatus(Status status){
        return suporteRepository.findByStatus(status);
    }

    public Suporte update(Integer id, Suporte suporte){
        Suporte update = findById(id);

        if(suporte.getTitulo() != null && !suporte.getTitulo().isBlank()){
            update.setTitulo(suporte.getTitulo());
        }

        if(suporte.getDescricao() != null && !suporte.getDescricao().isBlank()){
            update.setDescricao(suporte.getDescricao());
        }

        if(suporte.getTipo() != null){
            update.setTipo(suporte.getTipo());
        }

        if(suporte.getStatus() != null){
            update.setStatus(suporte.getStatus());
        }

        if(suporte.getUser() != null){
            update.setUser(suporte.getUser());
        }

        return suporteRepository.save(update);
    }

    public void delete(Integer id){
        Suporte delete = findById(id);
        suporteRepository.delete(delete);
    }
}