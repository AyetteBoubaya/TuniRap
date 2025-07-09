package org.example.serviceeventmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String id;
    private Long buyerId;
    private String buyerName;
    private BigDecimal price;
    private boolean paid;
    private String category; // "VIP" ou "NORMAL"
    private LocalDateTime purchaseDate;
    @JsonIgnore
    @JsonBackReference

    @DBRef
    private Event event;
    private boolean validated = false; // Check-in effectué ou pas

}







