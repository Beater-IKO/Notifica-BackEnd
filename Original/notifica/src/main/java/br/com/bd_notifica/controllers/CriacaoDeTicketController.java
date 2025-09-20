package br.com.bd_notifica.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.services.TicketService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller especializado para criação de tickets
 * Fornece endpoints específicos para o processo de abertura de chamados
 * Base URL: /criacao
 */
@RestController
@RequestMapping("/criacao")
public class CriacaoDeTicketController {

    // Injeção de dependência do serviço de tickets
    private final TicketService ticketService;

    public CriacaoDeTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Endpoint para criar novo ticket/chamado
     * POST /criacao/save
     * Aplica validações: problema obrigatório, usuário obrigatório
     * Define status inicial como ABERTO automaticamente
     */
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Ticket ticket) {
        var mensagem = ticketService.save(ticket);
        return new ResponseEntity<>(mensagem, HttpStatus.OK);
    }

    /**
     * Endpoint para consultar ticket criado
     * GET /criacao/findById/{id}
     * Permite acompanhar o ticket recém criado
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<Ticket> findById(@PathVariable Integer id) {
        var result = ticketService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
