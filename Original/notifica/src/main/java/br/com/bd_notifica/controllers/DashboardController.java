package br.com.bd_notifica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bd_notifica.services.TicketService;
import br.com.bd_notifica.services.UserService;
import br.com.bd_notifica.services.AuditService;
import br.com.bd_notifica.services.ReportService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// controlador para o painel administrativo (dashboard)
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:60058")
public class DashboardController {

    // serviços necessários para o dashboard
    private final TicketService ticketService;
    private final UserService userService;
    private final AuditService auditService;
    private final ReportService reportService;

    public DashboardController(TicketService ticketService, UserService userService, 
                             AuditService auditService, ReportService reportService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.auditService = auditService;
        this.reportService = reportService;
    }

    // busca estatísticas gerais do sistema
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // estatísticas de tickets
        stats.put("tickets", ticketService.getStatistics());
        
        // estatísticas de usuários
        Map<String, Object> userStats = new HashMap<>();
        userStats.put("total", userService.findAll().size());
        stats.put("users", userStats);
        
        return ResponseEntity.ok(stats);
    }

    // resumo dos tickets
    @GetMapping("/tickets/summary")
    public ResponseEntity<Map<String, Object>> getTicketSummary() {
        return ResponseEntity.ok(ticketService.getStatistics());
    }

    // atividade dos usuários
    @GetMapping("/users/activity")
    public ResponseEntity<Map<String, Object>> getUserActivity() {
        Map<String, Object> activity = new HashMap<>();
        activity.put("totalUsers", userService.findAll().size());
        activity.put("recentActions", auditService.getActionsByDateRange(
            LocalDateTime.now().minusDays(7), LocalDateTime.now()));
        return ResponseEntity.ok(activity);
    }

    // relatório mensal
    @GetMapping("/reports/monthly")
    public ResponseEntity<Map<String, Object>> getMonthlyReport() {
        Map<String, Object> report = new HashMap<>();
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        
        report.put("tickets", ticketService.getStatistics());
        report.put("users", Map.of("total", userService.findAll().size()));
        report.put("activities", auditService.getActionsByDateRange(startOfMonth, LocalDateTime.now()));
        
        return ResponseEntity.ok(report);
    }

    // relatório de desempenho
    @GetMapping("/reports/performance")
    public ResponseEntity<Map<String, Object>> getPerformanceReport() {
        return ResponseEntity.ok(reportService.generatePerformanceReport());
    }

    // relatório de atividade dos usuários
    @GetMapping("/reports/user-activity")
    public ResponseEntity<Map<String, Object>> getUserActivityReport(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : LocalDateTime.now().minusDays(30);
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : LocalDateTime.now();
        return ResponseEntity.ok(reportService.generateUserActivityReport(start, end));
    }

    // relatório de saúde do sistema
    @GetMapping("/reports/system-health")
    public ResponseEntity<Map<String, Object>> getSystemHealthReport() {
        return ResponseEntity.ok(reportService.generateSystemHealthReport());
    }
}