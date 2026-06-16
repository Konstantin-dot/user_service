package com.aston.service;

import com.aston.kafka.UserEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    private static final String TOPIC = "user-topic";

    public KafkaProducerService(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}