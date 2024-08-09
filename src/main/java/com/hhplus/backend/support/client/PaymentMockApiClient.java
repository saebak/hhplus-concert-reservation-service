package com.hhplus.backend.support.client;

import com.hhplus.backend.support.event.PaymentPayload;
import org.springframework.stereotype.Component;

@Component
public class PaymentMockApiClient {

    public String sendPaymentInfo(PaymentPayload payload) {
        return "[결제정보 저장 : " + payload.toString() +"]";
    }
}
