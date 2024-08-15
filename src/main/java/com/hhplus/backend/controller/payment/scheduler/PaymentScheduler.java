package com.hhplus.backend.controller.payment.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.domain.payment.PaymentService;
import com.hhplus.backend.support.client.PaymentMockApiClient;
import com.hhplus.backend.support.enums.EventType;
import com.hhplus.backend.support.event.PaymentPayload;
import com.hhplus.backend.support.event.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentScheduler {

    private final PaymentService paymentService;
    private final KafkaProducer kafkaProducer;

    private final PaymentMockApiClient paymentApiClient;

    @Scheduled(fixedRate = 5000)
    public void runUpdatePaymentEvent() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String aggregateType = "paymentEvent";
        List<PaymentOutbox> paymentOutboxs = paymentService.getPaymentOutboxsByStatusInit(aggregateType, "INIT");    // 쿼리로 시간 비교 , 실무에선 페이지네이션 필요(데이터 많으니깐)
        for (PaymentOutbox paymentOutbox : paymentOutboxs) {
            PaymentPayload payload = new PaymentPayload(paymentOutbox.getPayload());
            paymentApiClient.sendPaymentInfo(payload);
            kafkaProducer.publishPaymentInfo(paymentOutbox.getPayload());
        }
    }
}
