package org.example.serviceeventmanagement.Service;

import org.example.serviceeventmanagement.Entity.Event;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
public interface EventService {
    List<Event> getAllEvents(); // Récupérer tous les événements
    Optional<Event> getEventById(String id); // Récupérer un événement spécifique par ID
    Event createEvent(Event event); // Créer un événement
    Event updateEvent(String id, Event eventDetails); // Mettre à jour un événement
    void deleteEvent(String id); // Supprimer un événement
    Map<String, Integer> getRemainingTicketsByCategory(String eventId); // Récupérer les tickets restants par catégorie
    List<Event> findAvailableEvents(); // Récupérer les événements disponibles (tickets restants > 0)
    List<Event> getEventsSortedByPopularity(); // Trier les événements par popularité (tickets vendus)
    boolean isEventSoldOut(String eventId); // Vérifier si l'événement est complet
}
