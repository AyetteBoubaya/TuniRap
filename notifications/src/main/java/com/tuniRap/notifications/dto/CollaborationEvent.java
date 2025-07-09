package com.tuniRap.notifications.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CollaborationEvent {
    private Long collaborationId;
    private Long senderId;
    private Long receiverId;
    private String collaborationType;
    private String collaborationStatus;
    private String subject;
    private String description;
    private LocalDateTime creationDate;
    private String action; // "CREATED" ou "ACCEPTED"
}
