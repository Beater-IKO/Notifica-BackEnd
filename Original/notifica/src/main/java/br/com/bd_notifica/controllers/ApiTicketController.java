package br.com.bd_notifica.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.services.TicketService;

// controlador da API para mexer com os tickets do sistema
@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:60058")
public class ApiTicketController {

    // aqui fica o serviço que faz as operações com tickets
    private final TicketService ticketService;

    // construtor que recebe o serviço de tickets
    public ApiTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // endpoint para criar um ticket novo no sistema
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.save(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    // busca tickets usando filtros (prioridade, área, status, responsável)
    @GetMapping("/filter")
    public ResponseEntity<List<Ticket>> filterTickets(
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer assignedTo) {
        List<Ticket> tickets = ticketService.filterTickets(priority, area, status, assignedTo);
        return ResponseEntity.ok(tickets);
    }

    // passa um ticket para alguém resolver
    @PutMapping("/{id}/assign")
    public ResponseEntity<Ticket> assignTicket(@PathVariable Integer id, @RequestParam Integer userId) {
        Ticket ticket = ticketService.assignTicket(id, userId);
        return ResponseEntity.ok(ticket);
    }

    // muda o status do ticket (visto, em andamento, finalizado)
    @PutMapping("/{id}/status")
    public ResponseEntity<Ticket> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        Ticket ticket = ticketService.updateStatus(id, status);
        return ResponseEntity.ok(ticket);
    }

    // pega números e estatísticas dos tickets (quantos abertos, fechados, etc)
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics() {
        return ResponseEntity.ok(ticketService.getStatistics());
    }

    // mostra todos os tickets divididos em páginas (para não carregar tudo de uma vez)
    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        // define se vai ordenar crescente ou decrescente
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        // configura a paginação
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Ticket> ticketPage = ticketService.findAllPaginated(pageable);
        
        return ResponseEntity.ok(ticketPage.getContent());
    }

    // pega um ticket específico pelo número do ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Integer id) {
        Ticket ticket = ticketService.findById(id);
        return ResponseEntity.ok(ticket);
    }

    // edita as informações de um ticket existente
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Integer id, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.update(id, ticket);
        return ResponseEntity.ok(updatedTicket);
    }

    // apaga um ticket do sistema
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}