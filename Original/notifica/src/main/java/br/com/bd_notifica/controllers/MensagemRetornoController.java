package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.MensagemRetorno;
import br.com.bd_notifica.services.MensagemRetornoService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controller para mensagens de retorno
@RestController
@RequestMapping("/api/mensagemretorno")
@CrossOrigin(origins = "*")
public class MensagemRetornoController {

    private final MensagemRetornoService mensagemRetornoService;

    public MensagemRetornoController(MensagemRetornoService mensagemRetornoService) {
        this.mensagemRetornoService = mensagemRetornoService;
    }

    // Criar mensagem
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MensagemRetorno mensagemRetorno) {
        var result = mensagemRetornoService.save(mensagemRetorno);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // Listar mensagens
    @GetMapping("/findAll")
    public ResponseEntity<List<MensagemRetorno>> findAll() {
        var result = mensagemRetornoService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<MensagemRetorno> findById(@PathVariable Integer id) {
        var result = mensagemRetornoService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Mensagens de um ticket
    @GetMapping("/findByTicket/{id}")
    public ResponseEntity<List<MensagemRetorno>> findByTicket(@PathVariable Integer id) {
        var result = mensagemRetornoService.findByTicket(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Mensagens de um usuário
    @GetMapping("/findByUser/{nome}")
    public ResponseEntity<List<MensagemRetorno>> findByUser(@PathVariable String nome) {
        var result = mensagemRetornoService.findByUser(nome);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Excluir mensagem
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemRetorno> delete(@PathVariable Integer id) {
        mensagemRetornoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
