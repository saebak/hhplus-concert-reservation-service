package com.hhplus.backend.support.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentSuccessEvent {
    private final Long paymentId;
    private final Long userId;
    private final String status;
    private final int price;
    private final LocalDateTime createdAt;


    public PaymentSuccessEvent(Long paymentId, Long userId, int price, String status, LocalDateTime createdAt) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }
}
