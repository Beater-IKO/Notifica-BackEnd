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
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Criar curso
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Curso curso) { 
        try {
            var result = cursoService.save(curso);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Listar cursos
    @GetMapping("/findAll")
    public ResponseEntity<List<Curso>> findAll(){
        try{
            var result = cursoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Integer id){
        try {
            var result = cursoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Atualizar curso
    @PutMapping("/update/{id}")
    public ResponseEntity<Curso> update(@PathVariable Integer id, @RequestBody Curso curso){
        try {
            var result = cursoService.update(id, curso);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Excluir curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Integer id){
        try {
            cursoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}