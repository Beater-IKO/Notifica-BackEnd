package br.com.bd_notifica.services;

import org.springframework.stereotype.Service;

import br.com.bd_notifica.entities.Material;
import br.com.bd_notifica.repositories.MaterialRepository;

import java.util.List;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository){
        this.materialRepository = materialRepository;
    }

    public Material save(Material material){
        return materialRepository.save(material);
    }

    public List<Material> findAll(){
        return materialRepository.findAll();
    }

    public Material findById(Integer id){
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material não encontrado"));
    }

    public List<Material> findByNome(String nome){
        return materialRepository.findByNomeIgnoreCase(nome);
    }

    public List<Material> findByQuantidadeEstoqueGreaterThan(Integer quantidade){
        return materialRepository.findByQuantidadeEstoqueGreaterThan(quantidade);
    }

    public List<Material> findMateriaisComEstoque(Integer minimo){
        return materialRepository.findMateriaisComEstoque(minimo);
    }

    public Material update(Integer id, Material material){
        Material existingMaterial = findById(id);

        if(material.getNome() != null && !material.getNome().isBlank()){
            existingMaterial.setNome(material.getNome());
        }

        if(material.getDescricao() != null && !material.getDescricao().isBlank()){
            existingMaterial.setDescricao(material.getDescricao());
        }

        if(material.getQuantidadeEstoque() != null){
            existingMaterial.setQuantidadeEstoque(material.getQuantidadeEstoque());
        }

        return materialRepository.save(existingMaterial);
    }

    public void delete(Integer id) {
        Material materialToDelete = findById(id);
        materialRepository.delete(materialToDelete);
    }
}