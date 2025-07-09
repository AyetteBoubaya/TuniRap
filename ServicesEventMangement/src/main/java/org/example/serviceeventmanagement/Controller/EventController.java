package org.example.serviceeventmanagement.Controller;

import lombok.RequiredArgsConstructor;
import org.example.serviceeventmanagement.Entity.Event;
import org.example.serviceeventmanagement.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{eventId}/remaining-tickets")
    public ResponseEntity<Map<String, Integer>> getRemainingTickets(@PathVariable String eventId) {
        Map<String, Integer> remainingTickets = eventService.getRemainingTicketsByCategory(eventId);
        return ResponseEntity.ok(remainingTickets);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Event>> getAvailableEvents() {
        List<Event> availableEvents = eventService.findAvailableEvents();
        return ResponseEntity.ok(availableEvents);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Event>> getEventsSortedByPopularity() {
        List<Event> popularEvents = eventService.getEventsSortedByPopularity();
        return ResponseEntity.ok(popularEvents);
    }

    @GetMapping("/{eventId}/soldout")
    public ResponseEntity<Boolean> isEventSoldOut(@PathVariable String eventId) {
        boolean soldOut = eventService.isEventSoldOut(eventId);
        return ResponseEntity.ok(soldOut);
    }
}

