package br.com.bd_notifica.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@CrossOrigin(origins = "*")
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

        // Teste público GET
        @GetMapping("/public-test")
        public ResponseEntity<?> publicTestGet() {
                return ResponseEntity.ok(java.util.Map.of("message", "GET público funcionando"));
        }

        // Teste público POST
        @PostMapping("/public-test")
        public ResponseEntity<?> publicTest() {
                return ResponseEntity.ok(java.util.Map.of("message", "POST público funcionando"));
        }

        // Teste PATCH
        @PatchMapping("/test-patch")
        public ResponseEntity<?> testPatch(@RequestBody java.util.Map<String, Object> dados) {
                return ResponseEntity.ok(java.util.Map.of("message", "PATCH funcionando", "dados", dados));
        }

        // Debug - listar todos os tickets com seus status
        @GetMapping("/debug-status")
        public ResponseEntity<?> debugStatus() {
                List<Ticket> allTickets = ticketService.findAll();
                java.util.Map<String, Integer> statusCount = new java.util.HashMap<>();

                for (Ticket ticket : allTickets) {
                        String status = ticket.getStatus() != null ? ticket.getStatus().name() : "NULL";
                        statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
                }

                return ResponseEntity.ok(java.util.Map.of(
                                "totalTickets", allTickets.size(),
                                "statusCount", statusCount,
                                "tickets", allTickets.stream().map(t -> java.util.Map.of(
                                                "id", t.getId(),
                                                "problema", t.getProblema(),
                                                "status", t.getStatus() != null ? t.getStatus().name() : "NULL"))
                                                .toList()));
        }

        // Bypass completo para teste
        @PostMapping("/bypass-test")
        public ResponseEntity<?> bypassTest(@RequestBody java.util.Map<String, Object> dados) {
                System.out.println("=== BYPASS TEST CHAMADO ===");
                System.out.println("Dados recebidos: " + dados);
                return ResponseEntity.ok(java.util.Map.of("message", "Bypass funcionando", "dados", dados));
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
                                "authorities", user.getAuthorities().toString()));
        }

        // Criar ticket na rota principal
        @PostMapping
        public ResponseEntity<?> create(@RequestBody java.util.Map<String, Object> dados) {
                System.out.println("=== CONTROLLER CREATE CHAMADO ===");
                System.out.println("Dados recebidos: " + dados);

                try {
                        Ticket ticket = new Ticket();
                        ticket.setProblema((String) dados.get("problema"));

                        // Tentar pegar descrição ou usar problema como descrição
                        String descricao = (String) dados.get("descricao");
                        if (descricao == null || descricao.isBlank()) {
                                descricao = (String) dados.get("problema"); // usar problema como descrição
                        }
                        ticket.setDescricao(descricao);

                        String prioridadeStr = (String) dados.get("prioridade");
                        if (prioridadeStr != null) {
                                ticket.setPrioridade(br.com.bd_notifica.enums.GrauDePrioridade.valueOf(prioridadeStr));
                        }

                        // Tentar pegar salaId ou sala
                        Integer salaId = null;
                        Object salaObj = dados.get("salaId");
                        if (salaObj == null) {
                                salaObj = dados.get("sala");
                        }
                        if (salaObj != null) {
                                if (salaObj instanceof Integer) {
                                        salaId = (Integer) salaObj;
                                } else if (salaObj instanceof String) {
                                        salaId = Integer.parseInt((String) salaObj);
                                }
                        }
                        if (salaId != null) {
                                br.com.bd_notifica.entities.Sala sala = new br.com.bd_notifica.entities.Sala();
                                sala.setId(salaId);
                                ticket.setSala(sala);
                        }

                        // Usar usuário autenticado
                        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder
                                        .getContext().getAuthentication();
                        br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                        ticket.setUser(user);

                        Ticket ticketSalvo = ticketService.save(ticket);
                        return ResponseEntity
                                        .ok(java.util.Map.of("message", "Ticket criado", "id", ticketSalvo.getId()));
                } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
                }
        }

        // Endpoint de teste simples
        @GetMapping("/test")
        public ResponseEntity<?> test(org.springframework.security.core.Authentication auth) {
                if (auth == null) {
                        return ResponseEntity
                                        .ok(java.util.Map.of("message", "Sem autenticação", "authenticated", false));
                }

                br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();

                // Adicionar debug dos tickets aqui
                List<Ticket> allTickets = ticketService.findAll();
                java.util.Map<String, Integer> statusCount = new java.util.HashMap<>();

                for (Ticket ticket : allTickets) {
                        String status = ticket.getStatus() != null ? ticket.getStatus().name() : "NULL";
                        statusCount.put(status, statusCount.getOrDefault(status, 0) + 1);
                }

                return ResponseEntity.ok(java.util.Map.of(
                                "message", "Token válido!",
                                "authenticated", true,
                                "user", user.getEmail(),
                                "role", user.getRole().name(),
                                "totalTickets", allTickets.size(),
                                "statusCount", statusCount));
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

        // Buscar tickets por status
        @GetMapping("/status/{status}")
        public ResponseEntity<?> findByStatus(@PathVariable String status,
                        org.springframework.security.core.Authentication auth) {
                System.out.println("=== GET STATUS CHAMADO ===");
                System.out.println("Status solicitado: " + status);

                if (auth == null) {
                        System.out.println("ERRO: Authentication é null");
                        return ResponseEntity.status(401).body(java.util.Map.of("error", "Não autenticado"));
                }

                try {
                        br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                        System.out.println("Usuário: " + user.getEmail() + " | Role: " + user.getRole());

                        br.com.bd_notifica.enums.Status statusEnum = br.com.bd_notifica.enums.Status.valueOf(status);
                        System.out.println("Status enum: " + statusEnum);

                        List<Ticket> tickets;
                        if (user.getRole() == br.com.bd_notifica.enums.UserRole.ADMIN ||
                                        user.getRole() == br.com.bd_notifica.enums.UserRole.GESTOR) {
                                tickets = ticketService.findByStatus(statusEnum);
                                System.out.println("Admin/Gestor - Todos os tickets com status " + status + ": "
                                                + tickets.size());
                        } else {
                                tickets = ticketService.findByUserIdAndStatus(user.getId(), statusEnum);
                                System.out.println("Usuário " + user.getId() + " - Tickets com status " + status + ": "
                                                + tickets.size());
                        }

                        System.out.println("Retornando " + tickets.size() + " tickets");
                        return ResponseEntity.ok(tickets);
                } catch (IllegalArgumentException e) {
                        System.out.println("Status inválido: " + status);
                        return ResponseEntity.badRequest()
                                        .body(java.util.Map.of("error", "Status inválido: " + status));
                } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
                }
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

        // Alterar status do ticket
        @PutMapping("/{id}/status")
        @PatchMapping("/{id}/status")
        public ResponseEntity<?> updateStatus(@PathVariable Integer id,
                        @RequestBody java.util.Map<String, String> dados,
                        org.springframework.security.core.Authentication auth) {
                System.out.println("=== UPDATE STATUS CHAMADO ===");
                System.out.println("ID: " + id);
                System.out.println("Dados: " + dados);

                if (auth == null) {
                        System.out.println("ERRO: Authentication é null");
                        return ResponseEntity.status(401).body(java.util.Map.of("error", "Não autenticado"));
                }

                try {
                        br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                        System.out.println("Usuário: " + user.getEmail() + " | Role: " + user.getRole());

                        String novoStatus = dados.get("status");
                        if (novoStatus == null) {
                                return ResponseEntity.badRequest()
                                                .body(java.util.Map.of("error", "Status é obrigatório"));
                        }

                        System.out.println("Novo status: " + novoStatus);
                        br.com.bd_notifica.enums.Status statusEnum = br.com.bd_notifica.enums.Status
                                        .valueOf(novoStatus);
                        Ticket ticketAtualizado = ticketService.updateStatus(id, statusEnum);

                        System.out.println("Status atualizado com sucesso!");
                        return ResponseEntity.ok(java.util.Map.of(
                                        "message", "Status atualizado com sucesso",
                                        "ticketId", ticketAtualizado.getId(),
                                        "novoStatus", ticketAtualizado.getStatus().name()));
                } catch (IllegalArgumentException e) {
                        System.out.println("Erro - Status inválido: " + e.getMessage());
                        return ResponseEntity.badRequest()
                                        .body(java.util.Map.of("error", "Status inválido: " + dados.get("status")));
                } catch (Exception e) {
                        System.out.println("Erro geral: " + e.getMessage());
                        e.printStackTrace();
                        return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
                }
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

        // Listar tickets do usuário logado (rota principal) - DEVE SER O ÚNICO ENDPOINT
        // SEM PATH
        @GetMapping
        public ResponseEntity<?> getTickets(org.springframework.security.core.Authentication auth) {
                System.out.println("=== GET /api/tickets CHAMADO ===");

                if (auth == null) {
                        System.out.println("ERRO: Authentication é null");
                        return ResponseEntity.status(401).body(java.util.Map.of("error", "Não autenticado"));
                }

                try {
                        br.com.bd_notifica.entities.User user = (br.com.bd_notifica.entities.User) auth.getPrincipal();
                        System.out.println("Usuário autenticado: " + user.getEmail() + " | Role: " + user.getRole());

                        List<Ticket> tickets;
                        if (user.getRole() == br.com.bd_notifica.enums.UserRole.ADMIN ||
                                        user.getRole() == br.com.bd_notifica.enums.UserRole.GESTOR) {
                                tickets = ticketService.findAll();
                                System.out.println("Admin/Gestor - Buscando todos os tickets: " + tickets.size());
                        } else {
                                tickets = ticketService.findByUserId(user.getId());
                                System.out.println("Usuário comum - Buscando tickets do usuário " + user.getId() + ": "
                                                + tickets.size());
                        }
                        return ResponseEntity.ok(tickets);
                } catch (Exception e) {
                        System.out.println("ERRO no getTickets: " + e.getMessage());
                        e.printStackTrace();
                        return ResponseEntity.status(500).body(java.util.Map.of("error", e.getMessage()));
                }
        }
}
