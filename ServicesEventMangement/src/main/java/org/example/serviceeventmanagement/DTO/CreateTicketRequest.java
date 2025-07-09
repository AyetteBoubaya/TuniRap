package org.example.serviceeventmanagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTicketRequest {
    private String eventId;
    private Long userId;
}

