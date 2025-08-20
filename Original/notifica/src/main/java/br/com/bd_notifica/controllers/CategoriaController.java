package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.services.CategoriaService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller para categorias de tickets
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Criar categoria
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Categoria categoria) { 
        try {
            var result = categoriaService.save(categoria);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Listar categorias
    @GetMapping("/findAll")
    public ResponseEntity<List<Categoria>> findAll(){
        try{
            var result = categoriaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer id){
        try {
            var result = categoriaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar por nome
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<Categoria>> findByNome(@PathVariable String nome){
        try {
            var result = categoriaService.findByNome(nome);    
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Excluir categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> delete(@PathVariable Integer id){
        try {
            categoriaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
