package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Categoria;
import br.com.bd_notifica.services.CategoriaService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller para categorias de tickets
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Criar categoria
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Categoria categoria) {
        var result = categoriaService.save(categoria);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Listar categorias
    @GetMapping("/findAll")
    public ResponseEntity<List<Categoria>> findAll() {
        var result = categoriaService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
        var result = categoriaService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por nome
    @GetMapping("/findByNome/{nome}")
    public ResponseEntity<List<Categoria>> findByNome(@PathVariable String nome) {
        var result = categoriaService.findByNome(nome);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Excluir categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Categoria> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
