package com.tuniRap.notifications.service;

import com.tuniRap.notifications.dto.request.NotificationRequest;
import com.tuniRap.notifications.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    Notification createNotification(NotificationRequest notification);
    List<Notification> getNotificationsByRecipient(String recipientId);
    Optional<Notification> getNotificationById(String id);
    Notification markAsRead(String id);
    void deleteNotifcation(String id);
    void deleteNotificationsByRecipient(String recipientId);

}
