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
}
