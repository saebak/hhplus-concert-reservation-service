package com.hhplus.backend.controller.payment.mapper;

import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.infra.payment.entity.PaymentEntity;

public class PaymentMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static Payment toDomain(PaymentEntity entity) {
        return Payment.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .concertId(entity.getConcertId())
                .concertTitle(entity.getConcertTitle())
                .scheduleId(entity.getScheduleId())
                .seatId(entity.getSeatId())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static PaymentEntity toEntity(Payment domain) {
        PaymentEntity entity = new PaymentEntity();
        //entity.setId(domain.getId());
        entity.setUserId(domain.getSeatReservation().getUserId());
        entity.setConcertId(domain.getSeatReservation().getConcertId());
        entity.setConcertTitle(domain.getConcertTitle());
        entity.setScheduleId(domain.getSeatReservation().getScheduleId());
        entity.setSeatId(domain.getSeatReservation().getSeatId());
        entity.setPrice(domain.getPrice());
        entity.setStatus(domain.getStatus());
        return entity;
    }
}
