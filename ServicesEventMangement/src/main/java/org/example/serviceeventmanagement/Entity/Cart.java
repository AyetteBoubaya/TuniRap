package org.example.serviceeventmanagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;

    private Long userId; // Identifiant de l'utilisateur

    private List<Ticket> tickets = new ArrayList<>();

    private boolean isCheckedOut = false; // Est-ce que le panier est payé ?
    public Cart(Long userId) {
        this.userId = userId;
        this.tickets = new ArrayList<>();
        this.isCheckedOut = false;
    }
    // getters, setters, constructors
}

