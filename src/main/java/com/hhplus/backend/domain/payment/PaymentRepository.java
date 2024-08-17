package com.hhplus.backend.domain.payment;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PaymentRepository {

    // 결제
    Payment savePayment(Payment info);

    // 결제정보 조회
    List<Payment> getPayments(long userId);
    
    // 트랜잭션 로직 수행 후 outbox 저장
    PaymentOutbox savePaymentOutbox(PaymentOutbox outbox) throws JsonProcessingException;

    PaymentOutbox getPaymentOutbox(Long paymentId, String topic) throws JsonProcessingException;
    
    // 상태가 INIT인 outbox 데이터 조회
    List<PaymentOutbox> getPaymentOutboxsStatusInit(String aggregateType, String status) throws JsonProcessingException;

    List<PaymentOutbox> getPaymentOutboxs() throws JsonProcessingException;
}
