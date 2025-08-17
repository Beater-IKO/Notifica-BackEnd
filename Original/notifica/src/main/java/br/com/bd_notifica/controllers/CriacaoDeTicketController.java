package br.com.bd_notifica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/criacao")
public class CriacaoDeTicketController {

    private final TicketService ticketService;

    public CriacaoDeTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ticket ticket) {
        try {
            var mensagem = ticketService.save(ticket);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar o ticket", HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Integer id) {

        try {
            var result = ticketService.findById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

}
