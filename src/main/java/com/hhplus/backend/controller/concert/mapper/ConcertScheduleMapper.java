package com.hhplus.backend.controller.concert.mapper;

import com.hhplus.backend.domain.concert.Concert;
import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.infra.concert.entity.ConcertEntity;
import com.hhplus.backend.infra.concert.entity.ConcertScheduleEntity;

public class ConcertScheduleMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static ConcertSchedule toDomain(ConcertScheduleEntity entity) {
        return ConcertSchedule.builder()
                .id(entity.getId())
                .concertId(entity.getConcertId())
                .openDate(entity.getOpenDate())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static ConcertScheduleEntity toEntity(ConcertSchedule domain) {
        ConcertScheduleEntity entity = new ConcertScheduleEntity();
        entity.setId(domain.getId());
        entity.setConcertId(domain.getConcertId());
        entity.setOpenDate(domain.getOpenDate());
        return entity;
    }
}
