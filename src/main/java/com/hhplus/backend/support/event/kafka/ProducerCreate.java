package com.hhplus.backend.support.event.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerCreate {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerCreate(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create() {
        kafkaTemplate.send("topic", "hello kafka");
    }
}
