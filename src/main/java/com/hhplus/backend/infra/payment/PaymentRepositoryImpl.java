package com.hhplus.backend.infra.payment;

import com.hhplus.backend.controller.payment.dto.PaymentDto;
import com.hhplus.backend.controller.payment.mapper.PaymentMapper;
import com.hhplus.backend.controller.payment.mapper.PaymentOutboxMapper;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.infra.payment.entity.PaymentEntity;
import com.hhplus.backend.infra.payment.entity.PaymentOutboxEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private PaymentJpaRepository paymentJpaRepository;

    private PaymentOutboxJpaRepository paymentOutboxJpaRepository;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository, PaymentOutboxJpaRepository paymentOutboxJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentOutboxJpaRepository = paymentOutboxJpaRepository;
    }

    @Override
    public Payment savePayment(Payment info) {
        PaymentEntity entity = PaymentMapper.toEntity(info);
        entity = paymentJpaRepository.save(entity);
        Payment payment = PaymentMapper.toDomain(entity);
        return payment;
    }

    @Override
    public List<Payment> getPayments(long userId) {
        List<PaymentEntity> paymentEntities = paymentJpaRepository.findAllById(userId);
        List<Payment> payments = new ArrayList<>();
        for(PaymentEntity paymentEntity : paymentEntities) {
            payments.add(PaymentMapper.toDomain(paymentEntity));
        }
        return payments;
    }

    @Override
    public PaymentOutbox savePaymentOutbox(PaymentOutbox outbox) {
        PaymentOutboxEntity entity = PaymentOutboxMapper.toEntity(outbox);
        PaymentOutboxEntity resultEntity = paymentOutboxJpaRepository.save(entity);

        PaymentOutbox paymentOutbox = PaymentOutboxMapper.toDomain(resultEntity);
        return paymentOutbox;
    }
}
