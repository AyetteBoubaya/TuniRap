package org.example.serviceeventmanagement.Controller;

import org.example.serviceeventmanagement.Entity.Cart;
import org.example.serviceeventmanagement.Entity.Ticket;
import org.example.serviceeventmanagement.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    //  Voir le panier de l'utilisateur

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getUserCart(@PathVariable Long userId) {
        Cart cart = cartService.getUserCart(userId);
        return ResponseEntity.ok(cart);
    }

    //  Ajouter un ticket au panier
    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addTicketToCart(@PathVariable Long userId, @RequestBody Ticket ticket) {
        Cart cart = cartService.addTicketToCart(userId, ticket);
        return ResponseEntity.ok(cart);
    }

    //  Retirer un ticket du panier
    @DeleteMapping("/{userId}/remove/{ticketId}")
    public ResponseEntity<Cart> removeTicketFromCart(@PathVariable Long userId, @PathVariable String ticketId) {
        Cart cart = cartService.removeTicketFromCart(userId, ticketId);
        return ResponseEntity.ok(cart);
    }
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<Cart> checkoutAndPayCart(@PathVariable Long userId) {
        Cart checkedOutCart = cartService.checkoutAndPayCart(userId);
        return ResponseEntity.ok(checkedOutCart);
    }
}

