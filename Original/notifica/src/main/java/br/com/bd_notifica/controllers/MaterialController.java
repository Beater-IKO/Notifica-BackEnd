package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Material;
import br.com.bd_notifica.services.MaterialService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Material material) { 
        try {
            var result = materialService.save(material);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Material>> findAll(){
        try{
            var result = materialService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Material> findById(@PathVariable Integer id){
        try {
            var result = materialService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<Material>> findByNome(@PathVariable String nome){
        try {
            var result = materialService.findByNome(nome);    
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findComEstoque/{quantidade}")
    public ResponseEntity<List<Material>> findByQuantidadeEstoqueGreaterThan(@PathVariable Integer quantidade){
        try {
            var result = materialService.findByQuantidadeEstoqueGreaterThan(quantidade);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Material> update(@PathVariable Integer id, @RequestBody Material material){
        try {
            var result = materialService.update(id, material);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Material> delete(@PathVariable Integer id){
        try {
            materialService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}