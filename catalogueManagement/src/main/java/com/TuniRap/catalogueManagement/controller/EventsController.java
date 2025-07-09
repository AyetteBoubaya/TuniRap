// ayette was here
package com.TuniRap.catalogueManagement.controller;

import com.TuniRap.catalogueManagement.model.CatalogueEvent;
import com.TuniRap.catalogueManagement.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling catalogue events.
 * This controller demonstrates how to produce Kafka events from a REST API.
 */
@RestController
@RequestMapping("/api/events")
public class EventsController {
    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
    
    private final KafkaProducerService kafkaProducerService;
    
    @Value("${kafka.topic.catalogue-events}")
    private String catalogueEventsTopic;
    
    @Autowired
    public EventsController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }
    
    /**
     * Example 1: Basic event publishing from a REST endpoint
     * 
     * This endpoint demonstrates how to publish a Kafka event
     * received directly from an API client.
     * 
     * Example curl:
     * curl -X POST http://localhost:8089/api/events -H "Content-Type: application/json" 
     *      -d '{"eventType":"CREATED","entityId":"123","message":"New item created"}'
     */
    @PostMapping
    public ResponseEntity<String> publishEvent(@RequestBody CatalogueEvent event) {
        try {
            kafkaProducerService.sendCatalogueEvent(catalogueEventsTopic, event);
            return ResponseEntity.ok("Event published successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to publish event: " + e.getMessage());
        }
    }
    
    /**
     * Example 2: Creating and publishing an event from a simple operation
     * 
     * This endpoint demonstrates creating a Kafka event as part of
     * processing another operation.
     * 
     * Example curl:
     * curl -X POST http://localhost:8089/api/events/item/123/create?name=NewProduct
     */
    @PostMapping("/item/{itemId}/create")
    public ResponseEntity<String> createItem(
            @PathVariable String itemId,
            @RequestParam(required = false) String name) {
        
        logger.info("Creating item with ID: {} and name: {}", itemId, name);
        
        // In a real scenario, you would create the item in your database here
        
        // Create and publish an event for this operation
        CatalogueEvent event = new CatalogueEvent(
                "CREATED", 
                itemId,
                "Item created: " + (name != null ? name : "unnamed")
        );
        
        kafkaProducerService.sendCatalogueEvent(catalogueEventsTopic, event);
        
        return ResponseEntity.ok("Item created and event published");
    }
    
    /**
     * Example 3: Publishing an event for an update operation
     * 
     * Example curl:
     * curl -X PUT http://localhost:8089/api/events/item/123/update?name=UpdatedName
     */
    @PutMapping("/item/{itemId}/update")
    public ResponseEntity<String> updateItem(
            @PathVariable String itemId,
            @RequestParam(required = false) String name) {
        
        logger.info("Updating item with ID: {} to name: {}", itemId, name);
        
        // In a real scenario, you would update the item in your database here
        
        // Create and publish an event for this operation
        CatalogueEvent event = new CatalogueEvent(
                "UPDATED", 
                itemId,
                "Item updated: " + (name != null ? name : "unnamed")
        );
        
        kafkaProducerService.sendCatalogueEvent(catalogueEventsTopic, event);
        
        return ResponseEntity.ok("Item updated and event published");
    }
} 