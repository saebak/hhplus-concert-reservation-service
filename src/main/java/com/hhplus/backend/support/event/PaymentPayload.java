package com.hhplus.backend.support.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PaymentPayload {

    private Long paymentId;
    private Long userId;
    private int price;
    private String status;
    private LocalDateTime paymentDate;

    public PaymentPayload(PaymentSuccessEvent event) {
        this.paymentId = event.getPaymentId();
        this.userId = event.getUserId();
        this.price = event.getPrice();
        this.status = event.getStatus();
        this.paymentDate = event.getCreatedAt();
    }

    @Override
    public String toString() {
        return "PaymentPayload [paymentId=" + paymentId
                + ", userId=" + userId
                + ", price=" + price
                + ", status=" + status
                + ", paymentDate=" + paymentDate + "]";
    }

}
