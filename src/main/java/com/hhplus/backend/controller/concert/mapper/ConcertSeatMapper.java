package com.hhplus.backend.controller.concert.mapper;

import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.domain.concert.ConcertSeat;
import com.hhplus.backend.infra.concert.entity.ConcertScheduleEntity;
import com.hhplus.backend.infra.concert.entity.ConcertSeatEntity;

public class ConcertSeatMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static ConcertSeat toDomain(ConcertSeatEntity entity) {
        return ConcertSeat.builder()
                .id(entity.getId())
                .scheduleId(entity.getScheduleId())
                .seatNo(entity.getSeatNo())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static ConcertSeatEntity toEntity(ConcertSeat domain) {
        ConcertSeatEntity entity = new ConcertSeatEntity();
        entity.setId(domain.getId());
        entity.setScheduleId(domain.getScheduleId());
        entity.setSeatNo(domain.getSeatNo());
        return entity;
    }
}
