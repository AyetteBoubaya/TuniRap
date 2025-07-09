package com.tuniRap.collaborationManagement.service;

import com.tuniRap.collaborationManagement.model.Collaboration;
import com.tuniRap.collaborationManagement.model.enums.Type;

import java.util.List;
import java.util.Optional;

public interface CollaborationService {
    Collaboration createCollaborationRequest(Long senderId, Long receiverId, Type type, String subject, String description);
    Collaboration respondToCollaboration(Long collaborationId , boolean accepted, String responseMessage);
    //List<Collaboration> getCollaborations(Long userId);
    Optional<Collaboration> getCollaboration(Long collaborationId);
}
