package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.MensagemRetorno;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.enums.UserRole;
import br.com.bd_notifica.services.MensagemRetornoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mensagemretorno")
public class MensagemRetornoController {

    private final MensagemRetornoService mensagemRetornoService;

    public MensagemRetornoController(MensagemRetornoService mensagemRetornoService) {
        this.mensagemRetornoService = mensagemRetornoService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MensagemRetorno mensagemRetorno) { 
        try {
            var result = mensagemRetornoService.save(mensagemRetorno);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<MensagemRetorno>> findAll(){
        try{
            var result = mensagemRetornoService.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MensagemRetorno> findById(@PathVariable Integer id){
        try {
            var result = mensagemRetornoService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByTicket/{id}")
    public ResponseEntity<List<MensagemRetorno>> findByTicket(@PathVariable Integer id){
        try {
            var result = mensagemRetornoService.findByTicket(id);    
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByUser/{nome}")
    public ResponseEntity<List<MensagemRetorno>> findByUser(@PathVariable String nome){
        try {
            var result = mensagemRetornoService.findByUser(nome);    
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemRetorno> delete(@PathVariable Integer id){
        try {
            mensagemRetornoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
