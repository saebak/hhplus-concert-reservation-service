package com.hhplus.backend.support.event.kafka;

import com.hhplus.backend.support.event.PaymentPayload;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentInfo(PaymentPayload payload) {
        kafkaTemplate.send("paymentEvent", payload.toString());
    }
}
