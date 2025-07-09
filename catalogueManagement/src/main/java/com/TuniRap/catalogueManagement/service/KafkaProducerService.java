// ayette was here
package com.TuniRap.catalogueManagement.service;

import com.TuniRap.catalogueManagement.model.CatalogueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Service for producing Kafka messages.
 * This service handles sending CatalogueEvent objects to Kafka topics.
 */
@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    // Spring will inject a configured KafkaTemplate for sending messages
    private final KafkaTemplate<String, CatalogueEvent> kafkaTemplate;
    
    @Autowired
    public KafkaProducerService(KafkaTemplate<String, CatalogueEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    /**
     * Sends a CatalogueEvent to the specified Kafka topic.
     * 
     * @param topic The topic to send the event to
     * @param event The event to send
     */
    public void sendCatalogueEvent(String topic, CatalogueEvent event) {
        logger.info("Producing event to topic {}: {}", topic, event);
        
        // Send the event to Kafka and get a CompletableFuture for the result
        CompletableFuture<SendResult<String, CatalogueEvent>> future = kafkaTemplate.send(topic, event);
        
        // Handle the result asynchronously
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Event sent successfully to topic {} with offset {}", 
                          topic, result.getRecordMetadata().offset());
            } else {
                logger.error("Failed to send event to topic {}: {}", topic, ex.getMessage());
            }
        });
    }
} 