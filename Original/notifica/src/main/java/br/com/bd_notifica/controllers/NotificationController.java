package br.com.bd_notifica.controllers;

import br.com.bd_notifica.entities.Notification;
import br.com.bd_notifica.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// controlador para gerenciar notificações
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:60058")
public class NotificationController {

    // serviço de notificações
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // busca todas as notificações de um usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // busca apenas notificações não lidas
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // conta quantas notificações não lidas o usuário tem
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    // cria uma nova notificação
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> request) {
        Long userId = Long.valueOf(request.get("userId").toString());
        String title = request.get("title").toString();
        String message = request.get("message").toString();
        Notification.NotificationType type = Notification.NotificationType.valueOf(request.get("type").toString());

        Notification notification = notificationService.createNotification(userId, title, message, type);
        return ResponseEntity.ok(notification);
    }

    // marca notificação como lida
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(notification);
    }

    // exclui uma notificação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}