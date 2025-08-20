package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.repositories.SalaRepository;

import java.util.List;

// Serviço para salas
@Service
public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository){
        this.salaRepository = salaRepository;
    }

    // Salvar sala
    public Sala save(Sala sala){
        return salaRepository.save(sala);
    }

    // Listar salas
    public List<Sala> findAll(){
        return salaRepository.findAll();
    }

    // Buscar por ID
    public Sala findById(Integer id){
        return salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    // Atualizar sala
    public Sala update(Integer id, Sala sala){
        Sala existingSala = findById(id);

        if(sala.getNumero() != null && !sala.getNumero().isBlank()){
            existingSala.setNumero(sala.getNumero());
        }

        if(sala.getAndar() != null){
            existingSala.setAndar(sala.getAndar());
        }

        if(sala.getCurso() != null){
            existingSala.setCurso(sala.getCurso());
        }

        return salaRepository.save(existingSala);
    }

    // Excluir sala
    public void delete(Integer id) {
        Sala salaToDelete = findById(id);
        salaRepository.delete(salaToDelete);
    }
}