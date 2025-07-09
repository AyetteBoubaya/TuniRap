package com.tuniRap.collaborationManagement.dto;

import com.tuniRap.collaborationManagement.model.enums.Status;
import com.tuniRap.collaborationManagement.model.enums.Type;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CollaborationEvent {
    private Long collaborationId;
    private Long senderId;
    private Long receiverId;
    private Type collaborationType;
    private Status collaborationStatus;
    private String subject;
    private String description;
    private LocalDateTime creationDate;
    private String action; // "CREATED" ou "RESPONDED"
}
