package com.hhplus.backend.controller.concert.mapper;

import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;

import java.time.LocalDateTime;

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
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static SeatReservationEntity toEntity(SeatReservation domain) {
        SeatReservationEntity entity = new SeatReservationEntity();
        //entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setConcertId(domain.getConcertSchedule().getConcertId());
        entity.setScheduleId(domain.getConcertSchedule().getId());
        entity.setSeatId(domain.getSeat().getId());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return entity;
    }
}
