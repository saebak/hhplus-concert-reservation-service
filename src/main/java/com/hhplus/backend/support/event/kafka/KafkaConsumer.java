package com.hhplus.backend.support.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    private PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(PaymentRepository paymentRepository, ObjectMapper objectMapper) {
        this.paymentRepository = paymentRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "test")
    public void consume(String record) {
        log.info("Consumed message : {}", record);
    }

    @KafkaListener(topics = "paymentEvent", groupId = "payment")
    public void listener(String data) throws JsonProcessingException {
        log.info("==============payment message 소비");
        PaymentSuccessEvent event = objectMapper.readValue(data, PaymentSuccessEvent.class);
        String topic = "paymentEvent";

        PaymentOutbox outbox = paymentRepository.getPaymentOutbox(event.getPaymentId(), topic);
        outbox.publish(); // outbox 수정
        paymentRepository.savePaymentOutbox(outbox);
    }
}
