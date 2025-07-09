package org.example.serviceeventmanagement.Repository;

import org.example.serviceeventmanagement.Entity.Event;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}