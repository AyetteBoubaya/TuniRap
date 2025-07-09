package org.example.serviceeventmanagement.Repository;

import org.example.serviceeventmanagement.Entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByEventId(String eventId);
    List<Ticket> findByBuyerId(Long buyerId); // correction ici : List au lieu de Ticket
}




