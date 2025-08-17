package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.services.SuporteService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suportes")
public class SuporteController {

    private final SuporteService suporteService;

    public SuporteController(SuporteService suporteService) {
        this.suporteService = suporteService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Suporte suporte) { 
        try {
            var result = suporteService.save(suporte);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Suporte>> findAll(){
        try{
            var result = suporteService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Suporte> findById(@PathVariable Integer id){
        try {
            var result = suporteService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTipo/{tipo}")
    public ResponseEntity<List<Suporte>> findByTipo(@PathVariable String tipo){
        try {
            var result = suporteService.findByTipo(TipoSuporte.valueOf(tipo));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<List<Suporte>> findByStatus(@PathVariable String status){
        try {
            var result = suporteService.findByStatus(Status.valueOf(status));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Suporte> update(@PathVariable Integer id, @RequestBody Suporte suporte){
        try {
            var result = suporteService.update(id, suporte);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Suporte> delete(@PathVariable Integer id){
        try {
            suporteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}