package org.example.serviceeventmanagement.Service;

import org.example.serviceeventmanagement.DTO.UserDTO;
import org.example.serviceeventmanagement.Entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getTicketsByEvent(String eventId);
    UserDTO getUserOnlyById(Long userId);
    Ticket createAndBuyTicket(Long buyerId, String buyerName, String eventId, String category);
    //Vérifier la validité d’un ticket (pour check-in à l'entrée)
    boolean validateTicket(String ticketId);
    //Annuler un ticket
    Ticket cancelTicket(String ticketId);
}


