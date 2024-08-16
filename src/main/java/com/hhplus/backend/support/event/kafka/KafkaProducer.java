package com.hhplus.backend.support.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void create(String message) {
        log.info("Produce message : {}", message);
        kafkaTemplate.send("test", message);
    }

    public void publishPaymentInfo(PaymentSuccessEvent event) throws JsonProcessingException {
        log.info("Produce message : {}", event);
        kafkaTemplate.send("paymentEvent", objectMapper.writeValueAsString(event));
    }
}
