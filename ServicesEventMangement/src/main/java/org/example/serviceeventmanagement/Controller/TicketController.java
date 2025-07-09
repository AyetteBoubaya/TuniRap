package org.example.serviceeventmanagement.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.serviceeventmanagement.DTO.TicketRequest;
import org.example.serviceeventmanagement.DTO.UserDTO;
import org.example.serviceeventmanagement.Entity.Ticket;
import org.example.serviceeventmanagement.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @JsonIgnore
    @GetMapping("/by-event/{eventId}")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(ticketService.getTicketsByEvent(eventId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserOnlyById(@PathVariable Long userId) {
        UserDTO user = ticketService.getUserOnlyById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create-and-buy")
    public ResponseEntity<Ticket> createAndBuyTicket(@RequestBody TicketRequest request) {
        try {
            Ticket ticket = ticketService.createAndBuyTicket(
                    request.getBuyerId(),
                    request.getBuyerName(),
                    request.getEventId(),
                    request.getCategory()
            );
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
        // ✅ Valider un ticket (check-in à l'entrée)
        @PostMapping("/validate/{ticketId}")
        public ResponseEntity<String> validateTicket(@PathVariable String ticketId) {
            boolean validated = ticketService.validateTicket(ticketId);
            if (validated) {
                return ResponseEntity.ok("Ticket validé avec succès !");
            } else {
                return ResponseEntity.badRequest().body("Échec de la validation du ticket !");
            }
        }

        // ❌ Annuler un ticket
        @PostMapping("/cancel/{ticketId}")
        public ResponseEntity<String> cancelTicket(@PathVariable String ticketId) {
            try {
                ticketService.cancelTicket(ticketId);
                return ResponseEntity.ok("Ticket annulé avec succès !");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("Erreur lors de l'annulation du ticket : " + e.getMessage());
            }
        }

        // Autres méthodes existantes : créer un ticket, obtenir par event, etc.
    }


