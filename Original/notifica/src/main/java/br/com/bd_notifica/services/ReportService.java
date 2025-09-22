package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Ticket;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.repositories.TicketRepository;
import br.com.bd_notifica.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// serviço para gerar relatórios do sistema
@Service
public class ReportService {

    // repositórios e serviços necessários
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public ReportService(TicketRepository ticketRepository, UserRepository userRepository, AuditService auditService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    // gera relatório de desempenho do sistema
    @Transactional(readOnly = true)
    public Map<String, Object> generatePerformanceReport() {
        List<Ticket> allTickets = ticketRepository.findAll();
        List<User> allUsers = userRepository.findAll();
        
        Map<String, Object> report = new HashMap<>();
        
        // estatísticas gerais
        report.put("totalTickets", allTickets.size());
        report.put("totalUsers", allUsers.size());
        
        // tickets por usuário
        Map<String, Long> ticketsByUser = allTickets.stream()
            .filter(t -> t.getUser() != null)
            .collect(Collectors.groupingBy(t -> t.getUser().getNome(), Collectors.counting()));
        report.put("ticketsByUser", ticketsByUser);
        
        // tickets por status
        Map<String, Long> ticketsByStatus = allTickets.stream()
            .collect(Collectors.groupingBy(t -> t.getStatus().toString(), Collectors.counting()));
        report.put("ticketsByStatus", ticketsByStatus);
        
        // tickets por área
        Map<String, Long> ticketsByArea = allTickets.stream()
            .filter(t -> t.getArea() != null)
            .collect(Collectors.groupingBy(t -> t.getArea().toString(), Collectors.counting()));
        report.put("ticketsByArea", ticketsByArea);
        
        return report;
    }

    // gera relatório de atividade dos usuários
    @Transactional(readOnly = true)
    public Map<String, Object> generateUserActivityReport(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> report = new HashMap<>();
        
        // atividades do período
        var activities = auditService.getActionsByDateRange(startDate, endDate);
        report.put("totalActivities", activities.size());
        
        // atividades por tipo
        Map<String, Long> activitiesByType = activities.stream()
            .collect(Collectors.groupingBy(a -> a.getAction(), Collectors.counting()));
        report.put("activitiesByType", activitiesByType);
        
        // usuários mais ativos
        Map<String, Long> activitiesByUser = activities.stream()
            .filter(a -> a.getUserId() != null)
            .collect(Collectors.groupingBy(a -> a.getUserId().toString(), Collectors.counting()));
        report.put("activitiesByUser", activitiesByUser);
        
        return report;
    }

    // gera relatório de saúde do sistema
    @Transactional(readOnly = true)
    public Map<String, Object> generateSystemHealthReport() {
        Map<String, Object> report = new HashMap<>();
        
        List<Ticket> tickets = ticketRepository.findAll();
        List<User> users = userRepository.findAll();
        
        // métricas de saúde do sistema
        long openTickets = tickets.stream()
            .filter(t -> !t.getStatus().toString().equals("FINALIZADO"))
            .count();
        
        long activeUsers = users.stream()
            .filter(u -> u.getRole() != null)
            .count();
        
        report.put("openTickets", openTickets);
        report.put("activeUsers", activeUsers);
        report.put("systemLoad", calculateSystemLoad(tickets));
        report.put("generatedAt", LocalDateTime.now());
        
        return report;
    }

    // calcula a carga do sistema baseada em tickets urgentes
    private double calculateSystemLoad(List<Ticket> tickets) {
        long urgentTickets = tickets.stream()
            .filter(t -> t.getPrioridade() != null && t.getPrioridade().toString().equals("URGENTE"))
            .count();
        
        return tickets.isEmpty() ? 0.0 : (double) urgentTickets / tickets.size() * 100;
    }
}