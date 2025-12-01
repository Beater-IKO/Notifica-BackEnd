package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Suporte;
import br.com.bd_notifica.enums.TipoSuporte;
import br.com.bd_notifica.enums.Status;
import br.com.bd_notifica.services.SuporteService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller para chamados de suporte
@RestController
@RequestMapping("/api/suportes")
@CrossOrigin(origins = "*")
public class SuporteController {

    private final SuporteService suporteService;

    public SuporteController(SuporteService suporteService) {
        this.suporteService = suporteService;
    }

    // Criar chamado de suporte
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Suporte suporte) {
        var result = suporteService.save(suporte);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Listar chamados
    @GetMapping("/findAll")
    public ResponseEntity<List<Suporte>> findAll() {
        var result = suporteService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Suporte> findById(@PathVariable Integer id) {
        var result = suporteService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por tipo de suporte
    @GetMapping("/findByTipo/{tipo}")
    public ResponseEntity<List<Suporte>> findByTipo(@PathVariable String tipo) {
        var result = suporteService.findByTipo(TipoSuporte.valueOf(tipo));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Buscar por status
    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<List<Suporte>> findByStatus(@PathVariable String status) {
        var result = suporteService.findByStatus(Status.valueOf(status));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Atualizar chamado
    @PutMapping("/update/{id}")
    public ResponseEntity<Suporte> update(@PathVariable Integer id, @RequestBody Suporte suporte) {
        var result = suporteService.update(id, suporte);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Excluir chamado
    @DeleteMapping("/{id}")
    public ResponseEntity<Suporte> delete(@PathVariable Integer id) {
        suporteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}