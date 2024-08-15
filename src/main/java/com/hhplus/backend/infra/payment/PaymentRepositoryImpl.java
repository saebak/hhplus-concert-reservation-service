package com.hhplus.backend.infra.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hhplus.backend.controller.payment.dto.PaymentDto;
import com.hhplus.backend.controller.payment.mapper.PaymentMapper;
import com.hhplus.backend.controller.payment.mapper.PaymentOutboxMapper;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.infra.payment.entity.PaymentEntity;
import com.hhplus.backend.infra.payment.entity.PaymentOutboxEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public PaymentOutbox savePaymentOutbox(PaymentOutbox outbox) throws JsonProcessingException {
        PaymentOutboxEntity entity = PaymentOutboxMapper.toEntity(outbox);
        PaymentOutboxEntity resultEntity = paymentOutboxJpaRepository.save(entity);

        PaymentOutbox paymentOutbox = PaymentOutboxMapper.toDomain(resultEntity);
        return paymentOutbox;
    }

    @Override
    public PaymentOutbox getPaymentOutbox(Long paymentId, String topic) throws JsonProcessingException {
        PaymentOutboxEntity entity = paymentOutboxJpaRepository.findByAggregateIdAndAggregateType(paymentId, topic);

        PaymentOutbox paymentOutbox = PaymentOutboxMapper.toDomain(entity);
        return paymentOutbox;
    }

    @Override
    public List<PaymentOutbox> getPaymentOutboxsStatusInit(String aggregateType, String status) throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        List<PaymentOutboxEntity> entity = paymentOutboxJpaRepository.findAllByAggregateTypeAndStatus(aggregateType, status, now.minusMinutes(5));
        List<PaymentOutbox> paymentOutboxes = new ArrayList<>();
        for(PaymentOutboxEntity paymentOutboxEntity : entity) {
            paymentOutboxes.add(PaymentOutboxMapper.toDomain(paymentOutboxEntity));
        }
        return paymentOutboxes;
    }

    @Override
    public List<PaymentOutbox> getPaymentOutboxs() throws JsonProcessingException {
        List<PaymentOutboxEntity> entity = paymentOutboxJpaRepository.findAll();
        List<PaymentOutbox> paymentOutboxes = new ArrayList<>();
        for(PaymentOutboxEntity paymentOutboxEntity : entity) {
            paymentOutboxes.add(PaymentOutboxMapper.toDomain(paymentOutboxEntity));
        }
        return paymentOutboxes;
    }
}
