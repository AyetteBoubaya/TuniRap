package com.tuniRap.notifications.service.Impl;


import com.tuniRap.notifications.dto.request.NotificationRequest;
import com.tuniRap.notifications.model.Notification;
import com.tuniRap.notifications.repository.NotificationRepository;
import com.tuniRap.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientId(request.recipientId());
        notification.setTitle(request.title());
        notification.setContent(request.content());
        notification.setType(request.type());
        notification.setRelatedObjectId(request.relatedObjectId());

        notification.setCreatedAt(new Date());
        notification.setStatus("UNREAD");

        return notificationRepository.save(notification);
    }



    @Override
    public List<Notification> getNotificationsByRecipient(String recipientId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(recipientId);
    }

    @Override
    public Optional<Notification> getNotificationById(String id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification markAsRead(String id) {
        Notification notif = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        notif.setStatus("READ");
        notif.setReadAt((new Date()));
        return notificationRepository.save(notif);
    }

    @Override
    public void deleteNotifcation(String id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void deleteNotificationsByRecipient(String recipientId) {
        notificationRepository.deleteByRecipientId(recipientId);
    }
}
