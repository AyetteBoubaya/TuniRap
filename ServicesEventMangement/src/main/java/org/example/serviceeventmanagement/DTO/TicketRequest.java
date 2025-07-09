package org.example.serviceeventmanagement.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TicketRequest {
    private Long buyerId;
    private String buyerName;
    private String eventId;
    private String category;

    // Getters et setters
}







