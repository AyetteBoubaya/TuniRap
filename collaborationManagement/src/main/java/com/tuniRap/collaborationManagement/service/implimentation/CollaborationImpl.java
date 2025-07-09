package com.tuniRap.collaborationManagement.service.implimentation;

import com.tuniRap.collaborationManagement.dto.CollaborationEvent;
import com.tuniRap.collaborationManagement.event.CollaborationEventProducer;
import com.tuniRap.collaborationManagement.exception.AlreadyExistException;
import com.tuniRap.collaborationManagement.model.Collaboration;
import com.tuniRap.collaborationManagement.model.enums.Status;
import com.tuniRap.collaborationManagement.model.enums.Type;
import com.tuniRap.collaborationManagement.repository.CollaborationRepository;
import com.tuniRap.collaborationManagement.service.CollaborationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollaborationImpl implements CollaborationService {

    private final CollaborationRepository collaborationRepository;
    private final CollaborationEventProducer eventProducer;

    @Override
    public Collaboration createCollaborationRequest(Long senderId, Long receiverId, Type type, String subject, String description) {
        Collaboration collaboration = new Collaboration();
        collaboration.setSenderId(senderId);
        collaboration.setReceiverId(receiverId);
        collaboration.setCollaborationType(type);
        collaboration.setSubject(subject);
        collaboration.setDescription(description);
        collaboration.setCollaborationStatus(Status.PENDING);
        collaboration.setCreationDate(LocalDateTime.now());

        Collaboration savedCollab = collaborationRepository.save(collaboration);
        log.info("Création de collaboration entre {} et {}", senderId, receiverId);

        // ✅ Événement Kafka
        CollaborationEvent event = new CollaborationEvent();
        event.setCollaborationId(savedCollab.getId());
        event.setSenderId(senderId);
        event.setReceiverId(receiverId);
        event.setCollaborationType(type);
        event.setCollaborationStatus(Status.PENDING);
        event.setSubject(subject);
        event.setDescription(description);
        event.setCreationDate(savedCollab.getCreationDate());
        event.setAction("CREATED");

        eventProducer.sendEvent("collaboration-requests", event);

        return savedCollab;
    }

    @Override
    public Collaboration respondToCollaboration(Long collaborationId, boolean accepted, String responseMessage) {
        Collaboration collaboration = collaborationRepository.getCollaborationById(collaborationId);
        if (collaboration == null) {
            throw new AlreadyExistException("Collaboration not found ");
        }

        if (accepted) {
            collaboration.setCollaborationStatus(Status.ACCEPTED);
            log.info("Collaboration {} acceptée", collaborationId);

            // ✅ Événement Kafka
            CollaborationEvent event = new CollaborationEvent();
            event.setCollaborationId(collaboration.getId());
            event.setSenderId(collaboration.getSenderId());
            event.setReceiverId(collaboration.getReceiverId());
            event.setCollaborationType(collaboration.getCollaborationType());
            event.setCollaborationStatus(Status.ACCEPTED);
            event.setSubject(collaboration.getSubject());
            event.setDescription(collaboration.getDescription());
            event.setCreationDate(collaboration.getCreationDate());
            event.setAction("ACCEPTED");

            eventProducer.sendEvent("collaboration-accepted", event);

            // ⚠️ ici tu peux lancer l’ouverture du chat (mais via WebSocket le chat s’ouvre automatiquement via front)
        } else {
            collaboration.setCollaborationStatus(Status.REJECTED);
            log.info("Collaboration {} refusée", collaborationId);
        }

        return collaborationRepository.save(collaboration);
    }



    @Override
    public Optional<Collaboration> getCollaboration(Long collaborationId) {
        return collaborationRepository.findById(collaborationId);
    }


}

