package org.example.serviceeventmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
public class Event {

    @Id
    private String id;
    private String title;
    private String nomArtist;
    private String description;
    private String date; // ou LocalDate si tu préfères
    private String location;

    // Prix par catégorie : ex. { "VIP" : 100.00, "NORMAL" : 50.00 }
    private Map<String, BigDecimal> categoryPrices;

    // Nombre total de tickets par catégorie
    private Map<String, Integer> totalTicketsByCategory;

    // Tickets disponibles par catégorie
    private Map<String, Integer> remainingTicketsByCategory;

    private boolean validated = false;
    @JsonManagedReference
    @DBRef
    private List<Ticket> tickets = new ArrayList<>();
    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", nomArtist='" + nomArtist + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", categoryPrices=" + categoryPrices +
                ", totalTicketsByCategory=" + totalTicketsByCategory +
                ", remainingTicketsByCategory=" + remainingTicketsByCategory +
                ", validated=" + validated +
                '}';
    }
}







