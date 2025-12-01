package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Material;
import br.com.bd_notifica.services.MaterialService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// Controller para gestão de materiais
@RestController
@RequestMapping("/api/materiais")
@CrossOrigin(origins = "*")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    // Cadastrar material
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Material material) {
        var result = materialService.save(material);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Listar materiais
    @GetMapping("/findAll")
    public ResponseEntity<List<Material>> findAll() {
        var result = materialService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Material> findById(@PathVariable Integer id) {
        var result = materialService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por nome
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<Material>> findByNome(@PathVariable String nome) {
        var result = materialService.findByNome(nome);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Materiais com estoque maior que X
    @GetMapping("/findComEstoque/{quantidade}")
    public ResponseEntity<List<Material>> findByQuantidadeEstoqueGreaterThan(@PathVariable Integer quantidade) {
        var result = materialService.findByQuantidadeEstoqueGreaterThan(quantidade);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Atualizar material
    @PutMapping("/update/{id}")
    public ResponseEntity<Material> update(@PathVariable Integer id, @RequestBody Material material) {
        var result = materialService.update(id, material);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Excluir material
    @DeleteMapping("/{id}")
    public ResponseEntity<Material> delete(@PathVariable Integer id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}