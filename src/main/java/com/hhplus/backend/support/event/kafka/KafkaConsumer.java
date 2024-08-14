package com.hhplus.backend.support.event.kafka;

import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.domain.payment.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private PaymentRepository paymentRepository;

    public KafkaConsumer(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(topics = "paymentEvent", groupId = "group_1")
    public void listener(Object data) {
//        paymentRepository.findPaymentOutboxById(data).published() // outbox 수정
    }
}
