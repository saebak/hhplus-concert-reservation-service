package com.hhplus.backend.controller.payment.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentOutbox;
import com.hhplus.backend.infra.payment.entity.PaymentEntity;
import com.hhplus.backend.infra.payment.entity.PaymentOutboxEntity;
import com.hhplus.backend.support.event.PaymentSuccessEvent;

public class PaymentOutboxMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static PaymentOutbox toDomain(PaymentOutboxEntity entity) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return PaymentOutbox.builder()
                .id(entity.getId())
                .aggregateId(entity.getAggregateId())
                .aggregateType(entity.getAggregateType())
                .eventType(entity.getEventType())
                .payload(objectMapper.readValue(entity.getPayload(), PaymentSuccessEvent.class))
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
    public static PaymentOutboxEntity toEntity(PaymentOutbox domain) throws JsonProcessingException {
        PaymentOutboxEntity entity = new PaymentOutboxEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        entity.setId(domain.getId());
        entity.setAggregateId(domain.getAggregateId());
        entity.setAggregateType(domain.getAggregateType());
        entity.setEventType(domain.getEventType());
        entity.setPayload(objectMapper.writeValueAsString(domain.getPayload()));
        entity.setStatus(domain.getStatus());
        entity.setPublishedAt(domain.getPublishedAt());
        entity.setRetryCount(domain.getRetryCount());
        return entity;
    }
}
