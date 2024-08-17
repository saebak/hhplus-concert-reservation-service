package com.hhplus.backend.support.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.support.client.PaymentMockApiClient;
import com.hhplus.backend.support.enums.EventType;
import com.hhplus.backend.support.event.PaymentPayload;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentApiClientConsumer {

    private final PaymentMockApiClient paymentApiClient;
    private final PaymentRepository paymentRepository;

    public PaymentApiClientConsumer(PaymentMockApiClient paymentApiClient,
            PaymentRepository paymentRepository) {
        this.paymentApiClient = paymentApiClient;
        this.paymentRepository = paymentRepository;
    }

    @KafkaListener(topics = "paymentEvent", groupId = "paymentApiclient")
    public void listener(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentSuccessEvent event = mapper.readValue(data, PaymentSuccessEvent.class);
        PaymentPayload payload = new PaymentPayload(event);
        log.info("==============api client payment message 소비");
        String topic = "paymentEvent";

        // 외부 결제정보 저장 api 클라이언트 호출
        paymentApiClient.sendPaymentInfo(payload);

        PaymentOutbox outbox = paymentRepository.getPaymentOutbox(event.getPaymentId(), topic);
        outbox.publish(); // outbox 수정
        outbox.setEventType(EventType.SUCCESS.getResultMessage());
        paymentRepository.savePaymentOutbox(outbox);
    }
}
