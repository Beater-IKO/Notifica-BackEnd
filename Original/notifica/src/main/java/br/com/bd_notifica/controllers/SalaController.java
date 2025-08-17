package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.services.SalaService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Sala sala) { 
        try {
            var result = salaService.save(sala);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Sala>> findAll(){
        try{
            var result = salaService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Sala> findById(@PathVariable Integer id){
        try {
            var result = salaService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sala> update(@PathVariable Integer id, @RequestBody Sala sala){
        try {
            var result = salaService.update(id, sala);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sala> delete(@PathVariable Integer id){
        try {
            salaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}