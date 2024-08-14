package com.hhplus.backend.controller.payment.mapper;

import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.infra.payment.entity.PaymentEntity;
import com.hhplus.backend.infra.payment.entity.PaymentOutboxEntity;

public class PaymentOutboxMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static PaymentOutbox toDomain(PaymentOutboxEntity entity) {
        return PaymentOutbox.builder()
                .id(entity.getId())
                .aggregateId(entity.getAggregateId())
                .aggregateType(entity.getAggregateType())
                .eventType(entity.getEventType())
                .payload(entity.getPayload())
                .status(entity.getStatus())
                .publishedAt(entity.getPublishedAt())
                .retryCount(entity.getRetryCount())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static PaymentOutboxEntity toEntity(PaymentOutbox domain) {
        PaymentOutboxEntity entity = new PaymentOutboxEntity();
        entity.setId(domain.getId());
        entity.setAggregateId(domain.getAggregateId());
        entity.setAggregateType(domain.getAggregateType());
        entity.setEventType(domain.getEventType());
        entity.setPayload(domain.getPayload());
        entity.setStatus(domain.getStatus());
        entity.setPublishedAt(domain.getPublishedAt());
        entity.setRetryCount(domain.getRetryCount());
        return entity;
    }
}
