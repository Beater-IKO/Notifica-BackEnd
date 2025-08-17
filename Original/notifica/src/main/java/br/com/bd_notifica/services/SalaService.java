package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.repositories.SalaRepository;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository){
        this.salaRepository = salaRepository;
    }

    public Sala save(Sala sala){
        return salaRepository.save(sala);
    }

    public List<Sala> findAll(){
        return salaRepository.findAll();
    }

    public Sala findById(Integer id){
        return salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    public Sala update(Integer id, Sala sala){
        Sala update = findById(id);

        if(sala.getNumero() != null && !sala.getNumero().isBlank()){
            update.setNumero(sala.getNumero());
        }

        if(sala.getAndar() != null){
            update.setAndar(sala.getAndar());
        }

        if(sala.getCurso() != null){
            update.setCurso(sala.getCurso());
        }

        return salaRepository.save(update);
    }

    public void delete(Integer id) {
        Sala delete = findById(id);
        salaRepository.delete(delete);
    }
}