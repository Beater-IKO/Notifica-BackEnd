package br.com.bd_notifica.services;

import br.com.bd_notifica.entities.Notification;
import br.com.bd_notifica.entities.User;
import br.com.bd_notifica.repositories.NotificationRepository;
import br.com.bd_notifica.repositories.UserRepository;
import br.com.bd_notifica.config.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    // repositórios para acessar dados do banco
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    // busca todas as notificações de um usuário
    @Transactional(readOnly = true)
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // busca apenas as notificações não lidas
    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
    }

    // conta quantas notificações não lidas o usuário tem
    @Transactional(readOnly = true)
    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    // cria uma nova notificação para o usuário
    @Transactional
    public Notification createNotification(Long userId, String title, String message, Notification.NotificationType type) {
        User user = userRepository.findById(Math.toIntExact(userId))
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);

        return notificationRepository.save(notification);
    }

    // marca uma notificação como lida
    @Transactional
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Notificação não encontrada"));
        
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    // exclui uma notificação
    @Transactional
    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RecursoNaoEncontradoException("Notificação não encontrada");
        }
        notificationRepository.deleteById(notificationId);
    }
}