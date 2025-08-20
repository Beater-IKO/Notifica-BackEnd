package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.repositories.CategoriaRepository;

import java.util.List;
import org.springframework.stereotype.Service;

// Serviço para categorias
@Service
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    } 

    // Salvar categoria
    public Categoria save(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    // Listar todas as categorias
    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    // Buscar por ID
    public Categoria findById(Integer id){
        return categoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    // Buscar por nome
    public List<Categoria> findByNome(String nome){
        return categoriaRepository.findByNomeIgnoreCase(nome);
    }

    // Excluir categoria
    public void delete(Integer id){
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
    }

}
