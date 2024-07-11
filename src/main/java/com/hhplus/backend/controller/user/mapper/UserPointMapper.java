package com.hhplus.backend.controller.user.mapper;

import com.hhplus.backend.domain.user.User;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.infra.user.entity.UserEntity;
import com.hhplus.backend.infra.user.entity.UserPointEntity;

public class UserPointMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static UserPoint toDomain(UserPointEntity entity) {
        return UserPoint.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .point(entity.getPoint())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static UserPointEntity toEntity(UserPoint domain) {
        UserPointEntity entity = new UserPointEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setPoint(domain.getPoint());
        return entity;
    }
}
