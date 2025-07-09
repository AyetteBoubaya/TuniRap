package com.tuniRap.notifications.service;

import com.tuniRap.notifications.dto.CollaborationEvent;
import com.tuniRap.notifications.dto.request.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CollaborationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "collaboration-requests", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenCollaborationRequests(CollaborationEvent event) {
        log.info("NotificationService reçu une nouvelle demande de collaboration : {}", event);

        NotificationRequest request = new NotificationRequest(
                event.getReceiverId().toString(),
                "Nouvelle demande de collaboration",
                "Vous avez reçu une nouvelle demande de collaboration de l'utilisateur " + event.getSenderId() + " : " + event.getSubject(),
                "COLLABORATION_REQUEST",
                event.getCollaborationId().toString()
        );

        notificationService.createNotification(request);
    }

    @KafkaListener(topics = "collaboration-accepted", groupId = "notification-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenCollaborationAccepted(CollaborationEvent event) {
        log.info("NotificationService reçu une acceptation de collaboration : {}", event);

        NotificationRequest request = new NotificationRequest(
                event.getSenderId().toString(),
                "Collaboration acceptée",
                "Votre demande de collaboration '" + event.getSubject() + "' a été acceptée par l'utilisateur " + event.getReceiverId(),
                "COLLABORATION_ACCEPTED",
                event.getCollaborationId().toString()
        );

        notificationService.createNotification(request);
    }
}
