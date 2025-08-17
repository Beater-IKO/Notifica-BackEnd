package br.com.bd_notifica.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.services.CriacaoDeTicketService;

@RestController
@RequestMapping("/admin/tickets")
public class AdminTicketController {

    private final CriacaoDeTicketService criacaoDeTicketService;
    
    public AdminTicketController(CriacaoDeTicketService criacaoDeTicketService) {
        this.criacaoDeTicketService = criacaoDeTicketService;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Ticket ticket) {
        try {
            String mensagem = criacaoDeTicketService.save(ticket);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Ticket> tickets = criacaoDeTicketService.findAll();
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("API funcionando!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Integer id) {
        try {
            Ticket ticket = criacaoDeTicketService.findById(id);
            if (ticket != null) {
                return new ResponseEntity<>(ticket, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Ticket ticket) {
        try {
            String mensagem = criacaoDeTicketService.update(id, ticket);
            if (mensagem.contains("sucesso")) {
                return new ResponseEntity<>(mensagem, HttpStatus.OK);
            }
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar ticket", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            String mensagem = criacaoDeTicketService.delete(id);
            if (mensagem.contains("sucesso")) {
                return new ResponseEntity<>(mensagem, HttpStatus.OK);
            }
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar ticket", HttpStatus.BAD_REQUEST);
        }
    }
}