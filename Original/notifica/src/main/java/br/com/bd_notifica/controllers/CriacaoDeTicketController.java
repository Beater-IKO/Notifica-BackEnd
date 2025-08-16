package br.com.bd_notifica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.services.CriacaoDeTicketService;

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

    @Autowired
    private CriacaoDeTicketService criacaoDeTicketService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Ticket ticket) {
        try {
            String mensagem = criacaoDeTicketService.save(ticket);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar o ticket", HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Long id) {

        try {
            Ticket ticket = this.criacaoDeTicketService.findById(id);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

}
