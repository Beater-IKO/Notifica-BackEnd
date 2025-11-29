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

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

        private final TicketService ticketService;

        public TicketController(TicketService ticketService) {
                this.ticketService = ticketService;
        }

        // Criar novo ticket (admin)
        @PostMapping("/save")
        public ResponseEntity<Ticket> save(@RequestBody Ticket ticket) {

                Ticket ticketSalvo = ticketService.save(ticket);
                // ticketSalvo existe para armazenar temporariamente o resultado

                return new ResponseEntity<>(ticketSalvo, HttpStatus.CREATED);

        }

        // Teste público
        @PostMapping("/public-test")
        public ResponseEntity<?> publicTest() {
                return ResponseEntity.ok(java.util.Map.of("message", "Endpoint público funcionando"));
        }

        // Debug de autenticação
        @PostMapping("/debug-auth")
        public ResponseEntity<?> debugAuth(org.springframework.security.core.Authentication auth) {
                if (auth == null) {
                        return ResponseEntity.ok(java.util.Map.of("error", "Sem autenticação"));
                }
                
                br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                return ResponseEntity.ok(java.util.Map.of(
                        "authenticated", true,
                        "email", user.getEmail(),
                        "role", user.getRole().name(),
                        "authorities", user.getAuthorities().toString()
                ));
        }

        // Criar ticket na rota principal
        @PostMapping
        public ResponseEntity<Ticket> create(@RequestBody br.com.bd_notifica.dto.TicketCreateDTO dto, org.springframework.security.core.Authentication auth) {
                br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                
                Ticket ticket = new Ticket();
                ticket.setProblema(dto.getProblema());
                ticket.setDescricao(dto.getDescricao());
                ticket.setPrioridade(dto.getPrioridade());
                ticket.setUser(user);
                
                if (dto.getSalaId() != null) {
                        br.com.bd_notifica.entities.Sala sala = new br.com.bd_notifica.entities.Sala();
                        sala.setId(dto.getSalaId());
                        ticket.setSala(sala);
                }
                
                Ticket ticketSalvo = ticketService.save(ticket);
                return new ResponseEntity<>(ticketSalvo, HttpStatus.CREATED);
        }

        // Endpoint de teste simples
        @GetMapping("/test")
        public ResponseEntity<?> test(org.springframework.security.core.Authentication auth) {
                if (auth == null) {
                        return ResponseEntity
                                        .ok(java.util.Map.of("message", "Sem autenticação", "authenticated", false));
                }

                br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                return ResponseEntity.ok(java.util.Map.of(
                                "message", "Token válido!",
                                "authenticated", true,
                                "user", user.getEmail(),
                                "role", user.getRole().name()));
        }

        // Criar ticket simplificado para teste
        @PostMapping("/create-simple")
        public ResponseEntity<?> createSimple(@RequestBody java.util.Map<String, Object> dados,
                        org.springframework.security.core.Authentication auth) {
                try {
                        br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();

                        Ticket ticket = new Ticket();
                        ticket.setProblema((String) dados.get("problema"));
                        ticket.setDescricao((String) dados.get("descricao"));
                        ticket.setUser(user);

                        // Prioridade
                        String prioridadeStr = (String) dados.get("prioridade");
                        if (prioridadeStr != null) {
                                ticket.setPrioridade(br.com.bd_notifica.enums.GrauDePrioridade.valueOf(prioridadeStr));
                        }

                        // Sala obrigatória
                        Integer salaId = (Integer) dados.get("salaId");
                        if (salaId != null) {
                                br.com.bd_notifica.entities.Sala sala = new br.com.bd_notifica.entities.Sala();
                                sala.setId(salaId);
                                ticket.setSala(sala);
                        }

                        // Status padrão
                        ticket.setStatus(br.com.bd_notifica.enums.Status.INICIADO);

                        Ticket saved = ticketService.save(ticket);
                        return ResponseEntity.ok(java.util.Map.of(
                                        "message", "Ticket criado com sucesso!",
                                        "ticketId", saved.getId(),
                                        "problema", saved.getProblema()));
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body(java.util.Map.of(
                                        "error", e.getMessage(),
                                        "stackTrace", e.getStackTrace()[0].toString()));
                }
        }

        // Listar todos os tickets (admin) ou apenas do usuário logado
        @GetMapping("/findAll")
        public ResponseEntity<?> findAll(org.springframework.security.core.Authentication auth) {
                br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();

                List<Ticket> tickets;
                if (user.getRole() == br.com.bd_notifica.enums.UserRole.ADMIN ||
                                user.getRole() == br.com.bd_notifica.enums.UserRole.GESTOR) {
                        tickets = ticketService.findAll();
                } else {
                        tickets = ticketService.findByUserId(user.getId());
                }
                return ResponseEntity.ok(tickets);
        }

        // Buscar tickets por categoria
        @GetMapping("/findByCategoria/{id}")
        public ResponseEntity<List<Ticket>> findByCategoria(@PathVariable Integer id) {

                List<Ticket> tickets = ticketService.findByCategoriaId(id);
                return ResponseEntity.ok(tickets);

        }

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
}
