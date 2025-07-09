package org.example.serviceeventmanagement.Service;

import org.example.serviceeventmanagement.DTO.UserDTO;
import org.example.serviceeventmanagement.Entity.Event;
import org.example.serviceeventmanagement.Entity.Ticket;
import org.example.serviceeventmanagement.Repository.EventRepository;
import org.example.serviceeventmanagement.Repository.TicketRepository;
import org.example.serviceeventmanagement.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public List<Ticket> getTicketsByEvent(String eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    @Override
    public UserDTO getUserOnlyById(Long userId) {
        try {
            return userClient.getUserById(userId);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'appel Feign : " + e.getMessage());
            return null;
        }
    }
    @Override
    public Ticket createAndBuyTicket(Long buyerId, String buyerName, String eventId, String category) {
        // Vérifier que l'utilisateur existe
        if (!userExists(buyerId)) {
            throw new RuntimeException("Acheteur introuvable avec l'ID : " + buyerId);
        }

        // Charger l'événement
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable avec l'ID : " + eventId));

        // Vérifier la disponibilité du ticket
        Integer remainingTickets = event.getRemainingTicketsByCategory().getOrDefault(category, 0);
        if (remainingTickets <= 0) {
            throw new RuntimeException("Aucun ticket disponible pour la catégorie : " + category);
        }

        // Déterminer le prix
        BigDecimal price = event.getCategoryPrices().get(category);
        if (price == null) {
            throw new RuntimeException("Catégorie de ticket invalide : " + category);
        }

        // Créer le ticket
        Ticket ticket = Ticket.builder()
                .buyerId(buyerId)
                .buyerName(buyerName)
                .category(category)
                .price(price)
                .paid(true)
                .purchaseDate(LocalDateTime.now())
                .event(event)
                .build();

        // Sauvegarder le ticket
        Ticket savedTicket = ticketRepository.save(ticket);

        // Modifier l'événement : mettre à jour remainingTicketsByCategory
        event.getRemainingTicketsByCategory().put(category, remainingTickets - 1);

        // Ajouter le ticket dans la liste de tickets de l'événement
        if (event.getTickets() == null) {
            event.setTickets(new ArrayList<>());
        }
        event.getTickets().add(savedTicket);

        // Sauvegarder l'événement mis à jour
        eventRepository.save(event);

        return savedTicket;
    }

    // Méthode pour vérifier si l'utilisateur existe
    private boolean userExists(Long userId) {
        try {
            UserDTO user = userClient.getUserById(userId);
            return user != null;
        } catch (Exception e) {
            return false; // En cas d'erreur ou 404, on considère que l'utilisateur n'existe pas
        }
    }

    @Override
    public boolean validateTicket(String ticketId) {
        // Rechercher le ticket par ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket introuvable avec l'ID : " + ticketId));
        // Vérifier si le ticket est déjà utilisé (check-in déjà effectué)
        if (ticket.isValidated()) {
            throw new RuntimeException("Le ticket a déjà été validé !");
        }

        // Valider le ticket (check-in)
        ticket.setValidated(true);
        ticketRepository.save(ticket);

        return true; // Validation réussie
    }

    @Override
    public Ticket cancelTicket(String ticketId) {
        // Rechercher le ticket par ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket introuvable avec l'ID : " + ticketId));

        // Vérifier si le ticket est déjà annulé ou validé
        if (!ticket.isPaid()) {
            throw new RuntimeException("Le ticket est déjà annulé !");
        }
        if (ticket.isValidated()) {
            throw new RuntimeException("Impossible d'annuler un ticket déjà validé (check-in effectué) !");
        }

        // Marquer le ticket comme non payé pour simuler l'annulation
        ticket.setPaid(false);
        ticketRepository.save(ticket);

        // Mettre à jour le nombre de tickets restants dans l'événement
        Event event = ticket.getEvent();
        String category = ticket.getCategory();

        Integer remainingTickets = event.getRemainingTicketsByCategory().getOrDefault(category, 0);
        event.getRemainingTicketsByCategory().put(category, remainingTickets + 1);

        // Retirer le ticket de la liste des tickets de l'événement
        if (event.getTickets() != null) {
            event.getTickets().removeIf(t -> t.getId().equals(ticketId));
        }

        eventRepository.save(event);

        return ticket;
    }

    // Les autres méthodes existantes : getTicketsByEvent, createAndBuyTicket, etc.
}









