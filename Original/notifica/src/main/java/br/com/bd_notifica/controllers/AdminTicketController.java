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
import br.com.bd_notifica.services.TicketService;

@RestController
@RequestMapping("/admin/tickets")
public class AdminTicketController {

    private final TicketService ticketService;

    public AdminTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ticket ticket) {
        try {
            var mensagem = ticketService.save(ticket);
            return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<Ticket> tickets = ticketService.findAll();
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

    @GetMapping("/findById/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Integer id) {
        try {
            Ticket ticket = ticketService.findById(id);
            if (ticket != null) {
                return new ResponseEntity<>(ticket, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Ticket ticket) {
        try {
            var mensagem = ticketService.update(id, ticket);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar ticket", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Ticket> delete(@PathVariable Integer id) {
        try {
            ticketService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
           return ResponseEntity.noContent().build();
        }
    }
}