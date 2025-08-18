package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Protocolo;
import br.com.bd_notifica.enums.StatusProtocolo;
import br.com.bd_notifica.services.ProtocoloService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// API para requisições de materiais
@RestController
@RequestMapping("/protocolos")
public class ProtocoloController {

    private final ProtocoloService protocoloService;

    public ProtocoloController(ProtocoloService protocoloService) {
        this.protocoloService = protocoloService;
    }

    // Criar nova requisição
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Protocolo protocolo) { 
        try {
            var result = protocoloService.save(protocolo);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Listar todas as requisições
    @GetMapping("/findAll")
    public ResponseEntity<List<Protocolo>> findAll(){
        try{
            var result = protocoloService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Protocolo> findById(@PathVariable Integer id){
        try {
            var result = protocoloService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Filtrar por status (PENDENTE, APROVADO, etc)
    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<List<Protocolo>> findByStatus(@PathVariable String status){
        try {
            var result = protocoloService.findByStatus(StatusProtocolo.valueOf(status));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Ver requisições de um usuário
    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<List<Protocolo>> findByUserId(@PathVariable Integer userId){
        try {
            var result = protocoloService.findByUserId(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Atualizar requisição
    @PutMapping("/update/{id}")
    public ResponseEntity<Protocolo> update(@PathVariable Integer id, @RequestBody Protocolo protocolo){
        try {
            var result = protocoloService.update(id, protocolo);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Cancelar requisição
    @DeleteMapping("/{id}")
    public ResponseEntity<Protocolo> delete(@PathVariable Integer id){
        try {
            protocoloService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}