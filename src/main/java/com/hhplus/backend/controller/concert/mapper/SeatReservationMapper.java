package com.hhplus.backend.controller.concert.mapper;

import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;

public class SeatReservationMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static SeatReservation toDomain(SeatReservationEntity entity) {
        return SeatReservation.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .concertId(entity.getConcertId())
                .scheduleId(entity.getScheduleId())
                .seatId(entity.getSeatId())
                .createAt(entity.getCreatedAt())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static SeatReservationEntity toEntity(SeatReservation domain) {
        SeatReservationEntity entity = new SeatReservationEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setConcertId(domain.getConcertId());
        entity.setScheduleId(domain.getScheduleId());
        entity.setSeatId(domain.getSeatId());
        entity.setCreatedAt(domain.getCreateAt());
        return entity;
    }
}
