package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Sala;
import br.com.bd_notifica.services.SalaService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Sala sala) {
        var result = salaService.save(sala);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Sala>> findAll() {
        var result = salaService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Sala> findById(@PathVariable Integer id) {
        var result = salaService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Sala> update(@PathVariable Integer id, @RequestBody Sala sala) {
        var result = salaService.update(id, sala);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Sala> delete(@PathVariable Integer id) {
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}