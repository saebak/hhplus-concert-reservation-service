package com.hhplus.backend.controller.concert.mapper;

import com.hhplus.backend.domain.concert.Concert;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.infra.concert.entity.ConcertEntity;
import com.hhplus.backend.infra.user.entity.UserPointEntity;

public class ConcertMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static Concert toDomain(ConcertEntity entity) {
        return Concert.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .content(entity.getContent())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static ConcertEntity toEntity(Concert domain) {
        ConcertEntity entity = new ConcertEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setPrice(domain.getPrice());
        entity.setContent(domain.getContent());
        return entity;
    }
}
