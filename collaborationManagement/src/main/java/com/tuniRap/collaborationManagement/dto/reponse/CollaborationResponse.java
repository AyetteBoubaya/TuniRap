package com.tuniRap.collaborationManagement.dto.reponse;

public record CollaborationResponse(
        boolean accepted,
        String responseMessage
) {
}
