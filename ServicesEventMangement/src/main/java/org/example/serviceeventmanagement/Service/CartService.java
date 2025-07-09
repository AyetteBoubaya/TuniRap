package org.example.serviceeventmanagement.Service;

import org.example.serviceeventmanagement.Entity.Cart;
import org.example.serviceeventmanagement.Entity.Ticket;

public interface CartService {
    Cart getUserCart(Long userId);  // Récupérer le panier d'un utilisateur
    Cart addTicketToCart(Long userId, Ticket ticket);  // Ajouter un ticket au panier
    Cart removeTicketFromCart(Long userId, String ticketId);  // Retirer un ticket du panier
    Cart checkoutAndPayCart(Long userId);  // Finaliser et payer le panier
}



