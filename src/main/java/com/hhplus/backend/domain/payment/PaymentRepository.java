package com.hhplus.backend.domain.payment;

import com.hhplus.backend.controller.payment.dto.PayInput;

public interface PaymentRepository {

    // 결제
    Payment pay(PayInput input);

    // 결제정보 조회
//    List<Payment> getPaymentInfo(long userId);
}
