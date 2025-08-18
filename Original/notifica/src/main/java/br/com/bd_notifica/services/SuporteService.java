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
        // Validação de campos obrigatórios
        if(suporte.getTitulo() == null || suporte.getTitulo().isBlank()){
            throw new RuntimeException("Título é obrigatório");
        }
        
        if(suporte.getDescricao() == null || suporte.getDescricao().isBlank()){
            throw new RuntimeException("Descrição é obrigatória");
        }
        
        // Atribui status automaticamente baseado no tipo
        if(suporte.getStatus() == null){
            suporte.setStatus(Status.VISTO);
        }
        
        // Tipos críticos recebem prioridade maior
        if(suporte.getTipo() == TipoSuporte.TECNICO){
            suporte.setStatus(Status.EM_ANDAMENTO);
        }
        
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
        Suporte existingSuport = findById(id);
        
        // Não permite modificar suportes finalizados
        if(existingSuport.getStatus() == Status.FINALIZADOS){
            throw new RuntimeException("Não é possível modificar suporte finalizado");
        }

        if(suporte.getTitulo() != null && !suporte.getTitulo().isBlank()){
            existingSuport.setTitulo(suporte.getTitulo());
        }

        if(suporte.getDescricao() != null && !suporte.getDescricao().isBlank()){
            existingSuport.setDescricao(suporte.getDescricao());
        }

        if(suporte.getTipo() != null){
            existingSuport.setTipo(suporte.getTipo());
        }

        if(suporte.getStatus() != null){
            existingSuport.setStatus(suporte.getStatus());
        }

        if(suporte.getUser() != null){
            existingSuport.setUser(suporte.getUser());
        }

        return suporteRepository.save(existingSuport);
    }

    public void delete(Integer id){
        Suporte suporteToDelete = findById(id);
        
        // Não permite excluir suportes em andamento
        if(suporteToDelete.getStatus() == Status.EM_ANDAMENTO){
            throw new RuntimeException("Não é possível excluir suporte em andamento");
        }
        
        suporteRepository.delete(suporteToDelete);
    }
}