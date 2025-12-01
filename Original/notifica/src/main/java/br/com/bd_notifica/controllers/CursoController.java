package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Curso;
import br.com.bd_notifica.services.CursoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// Controller para gestão de cursos
@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Criar curso
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Curso curso) {
        var result = cursoService.save(curso);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Listar cursos
    @GetMapping("/findAll")
    public ResponseEntity<List<Curso>> findAll() {
        var result = cursoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Integer id) {
        var result = cursoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Atualizar curso
    @PutMapping("/update/{id}")
    public ResponseEntity<Curso> update(@PathVariable Integer id, @RequestBody Curso curso) {
        var result = cursoService.update(id, curso);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Excluir curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Integer id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}