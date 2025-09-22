package br.com.bd_notifica.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

// Controller administrativo para gestão de tickets
@RestController
@RequestMapping("/admin/tickets/")
@CrossOrigin(origins = "http://localhost:60058")
public class AdminTicketController {

        private final TicketService ticketService;

        public AdminTicketController(TicketService ticketService) {
                this.ticketService = ticketService;
        }

        // Criar novo ticket (admin)
        @PostMapping("/save")
        public ResponseEntity<Ticket> save(@RequestBody Ticket ticket) {

                Ticket ticketSalvo = ticketService.save(ticket);
                return new ResponseEntity<>(ticketSalvo, HttpStatus.CREATED);

        }

        // Listar todos os tickets
        @GetMapping("/findAll")
        public ResponseEntity<?> findAll() {

                List<Ticket> tickets = ticketService.findAll();
                return ResponseEntity.ok(tickets);

        }

        // Buscar tickets por categoria
        @GetMapping("/findByCategoria/{id}")
        public ResponseEntity<List<Ticket>> findByCategoria(@PathVariable Integer id) {

                List<Ticket> tickets = ticketService.findByCategoriaId(id);
                return ResponseEntity.ok(tickets);

        }

        // buscar ticket por ID
        @GetMapping("/findById/{id}")
        public ResponseEntity<Ticket> findById(@PathVariable Integer id) {

                Ticket ticket = ticketService.findById(id);
                return ResponseEntity.ok(ticket);

        }

        // Atualizar ticket
        @PutMapping("/update/{id}")
        public ResponseEntity<Ticket> update(@PathVariable Integer id, @RequestBody Ticket ticket) {

                Ticket ticketAtualizado = ticketService.update(id, ticket);
                return ResponseEntity.ok(ticketAtualizado);

        }

        // Excluir ticket
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> delete(@PathVariable Integer id) {

                ticketService.delete(id);

                return ResponseEntity.noContent().build();

        }

        // Teste da API
        @GetMapping("/test")
        public ResponseEntity<String> test() {
                return new ResponseEntity<>("API funcionando!", HttpStatus.OK);
        }
}
