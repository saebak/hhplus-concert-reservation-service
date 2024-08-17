package com.hhplus.backend.support.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {
    private final ApplicationEventPublisher publisher;

    public PaymentEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void success(PaymentSuccessEvent paymentSuccessEvent) {
        publisher.publishEvent(paymentSuccessEvent);
    }

    // outbox 패턴으로 바꾸면서 publish로 명칭 변경
    public void publish(PaymentSuccessEvent paymentSuccessEvent) {
        publisher.publishEvent(paymentSuccessEvent);
    }
}
