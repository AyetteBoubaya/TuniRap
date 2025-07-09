package com.tuniRap.collaborationManagement.dto.request;

import com.tuniRap.collaborationManagement.model.enums.Type;

public record CollaborationRequest (

    Long senderId,
    Long receiverId,
    Type collaborationType,
    String subject,
    String description
) {}
