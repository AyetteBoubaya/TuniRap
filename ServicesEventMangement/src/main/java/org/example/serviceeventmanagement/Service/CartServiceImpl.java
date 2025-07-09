package org.example.serviceeventmanagement.Service;

import org.example.serviceeventmanagement.Entity.Cart;
import org.example.serviceeventmanagement.Entity.Ticket;
import org.example.serviceeventmanagement.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Override
    public Cart getUserCart(Long userId) {
        return cartRepository.findByUserIdAndIsCheckedOutFalse(userId)
                .orElse(new Cart(userId));
    }
    @Override
    public Cart addTicketToCart(Long userId, Ticket ticket) {
        Cart cart = getUserCart(userId);
        cart.getTickets().add(ticket);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeTicketFromCart(Long userId, String ticketId) {
        Cart cart = getUserCart(userId);
        cart.getTickets().removeIf(ticket -> ticket.getId().equals(ticketId));
        return cartRepository.save(cart);
    }

    @Override
    public Cart checkoutAndPayCart(Long userId) {
        Cart cart = getUserCart(userId);
        cart.setCheckedOut(true);
        return cartRepository.save(cart);
    }
}



