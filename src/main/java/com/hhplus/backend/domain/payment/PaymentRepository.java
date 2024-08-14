package com.hhplus.backend.domain.payment;

import com.hhplus.backend.controller.payment.dto.PaymentDto;

import java.util.List;

public interface PaymentRepository {

    // 결제
    Payment savePayment(Payment info);

    // 결제정보 조회
    List<Payment> getPayments(long userId);
    
    // 트랜잭션 로직 수행 후 outbox 저장
    PaymentOutbox savePaymentOutbox(PaymentOutbox outbox);
}
