package com.hhplus.backend.support.event.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.support.client.PaymentMockApiClient;
import com.hhplus.backend.support.event.PaymentPayload;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentApiClientConsumer {

    private final PaymentMockApiClient paymentApiClient;

    public PaymentApiClientConsumer(PaymentMockApiClient paymentApiClient) {
        this.paymentApiClient = paymentApiClient;
    }

    @KafkaListener(topics = "paymentEvent", groupId = "payment", containerFactory = "kafkaListenerContainerFactory")
    public void listener(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentSuccessEvent event = mapper.readValue(data, PaymentSuccessEvent.class);
        PaymentPayload payload = new PaymentPayload(event);

        // 외부 결제정보 저장 api 클라이언트 호출
        paymentApiClient.sendPaymentInfo(payload);
    }
}
