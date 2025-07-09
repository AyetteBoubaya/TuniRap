package com.tuniRap.notifications.controller;


import com.tuniRap.notifications.dto.request.NotificationRequest;
import com.tuniRap.notifications.model.Notification;
import com.tuniRap.notifications.repository.NotificationRepository;
import com.tuniRap.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/createNotification")
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationRequest notification) {
        return ResponseEntity.ok(notificationService.createNotification(notification));
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<Notification>> getNotificationsByRecipient(@PathVariable String recipientId) {
        return ResponseEntity.ok(notificationService.getNotificationsByRecipient(recipientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable String id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable String id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String id) {
        notificationService.deleteNotifcation(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/recipient/{recipientId}")
    public ResponseEntity<Void> deleteNotificationsByRecipient(@PathVariable String recipientId) {
        notificationService.deleteNotificationsByRecipient(recipientId);
        return ResponseEntity.noContent().build();
    }
}

