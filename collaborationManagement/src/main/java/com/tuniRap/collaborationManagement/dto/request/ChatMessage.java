package com.tuniRap.collaborationManagement.dto.request;


import com.tuniRap.collaborationManagement.configurations.chat.MessageType;
import com.tuniRap.collaborationManagement.model.enums.Type;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ChatMessage {

    private Long senderId;
    private Long collaborationId;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;
}