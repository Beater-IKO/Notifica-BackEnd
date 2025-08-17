package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.repositories.CategoriaRepository;
import br.com.bd_notifica.repositories.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;


    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    } 

    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    public Categoria findById(Integer id){
        return categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Categoria> findByNome(String nome){
        return categoriaRepository.findByNomeIgnoreCase(nome);
    }

    public List<Categoria> findByCategoria(String categoria){
        return categoriaRepository.findByCategoriaIgnoreCase(categoria);
    }

    public void delete(Integer id){
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
    }
}
