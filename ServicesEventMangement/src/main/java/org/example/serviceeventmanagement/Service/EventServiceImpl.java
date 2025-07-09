package org.example.serviceeventmanagement.Service;

import org.example.serviceeventmanagement.Entity.Event;
import org.example.serviceeventmanagement.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event createEvent(Event event) {
        event.setValidated(false);
        event.setRemainingTicketsByCategory(new HashMap<>(event.getTotalTicketsByCategory()));
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(String id, Event eventDetails) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setTitle(eventDetails.getTitle());
            event.setDescription(eventDetails.getDescription());
            event.setDate(eventDetails.getDate()); // Assurez-vous que `date` est de type `LocalDate`
            event.setLocation(eventDetails.getLocation());
            event.setValidated(eventDetails.isValidated());
            event.setCategoryPrices(eventDetails.getCategoryPrices());
            event.setTotalTicketsByCategory(eventDetails.getTotalTicketsByCategory());
            event.setRemainingTicketsByCategory(new HashMap<>(eventDetails.getTotalTicketsByCategory()));
            return eventRepository.save(event);
        } else {
            throw new RuntimeException("Événement introuvable avec l'ID : " + id);
        }
    }

    @Override
    public Map<String, Integer> getRemainingTicketsByCategory(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));
        return event.getRemainingTicketsByCategory();
    }

    @Override
    public List<Event> findAvailableEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream()
                .filter(event -> event.getRemainingTicketsByCategory() != null &&
                        event.getRemainingTicketsByCategory()
                                .values()
                                .stream()
                                .anyMatch(tickets -> tickets > 0))
                .toList();
    }

    @Override
    public boolean isEventSoldOut(String eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement introuvable"));
        return event.getRemainingTicketsByCategory().values().stream().allMatch(tickets -> tickets <= 0);
    }


    @Override
    public List<Event> getEventsSortedByPopularity() {
        List<Event> allEvents = eventRepository.findAll();
        return allEvents.stream()
                .sorted((e1, e2) -> Integer.compare(
                        totalTicketsSold(e2), totalTicketsSold(e1)
                ))
                .toList();
    }

    private int totalTicketsSold(Event event) {
        if (event.getTotalTicketsByCategory() == null || event.getRemainingTicketsByCategory() == null) {
            return 0;
        }
        return event.getTotalTicketsByCategory().entrySet().stream()
                .mapToInt(entry -> entry.getValue() - event.getRemainingTicketsByCategory().getOrDefault(entry.getKey(), 0))
                .sum();
    }
}
