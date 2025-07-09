package com.tuniRap.collaborationManagement.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollaborationEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void sendEvent(String topic, Object event) {
        log.info("Sending event to topic {}: {}", topic, event);
        kafkaTemplate.send(topic, event);
    }
}

