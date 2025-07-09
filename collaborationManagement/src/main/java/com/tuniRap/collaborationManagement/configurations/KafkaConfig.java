package com.tuniRap.collaborationManagement.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic collaborationRequestsTopic() {
        return new NewTopic("collaboration-requests", 1, (short) 1);
    }

    @Bean
    public NewTopic collaborationAcceptedTopic() {
        return new NewTopic("collaboration-accepted", 1, (short) 1);
    }
}

