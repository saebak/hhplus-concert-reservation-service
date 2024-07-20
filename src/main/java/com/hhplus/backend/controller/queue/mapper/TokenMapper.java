package com.hhplus.backend.controller.queue.mapper;

import com.hhplus.backend.domain.queue.UserToken;
import com.hhplus.backend.infra.queue.entity.UserTokenEntity;

public class TokenMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static UserToken toDomain(UserTokenEntity entity) {
        return UserToken.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .accessToken(entity.getAccessToken())
                .status(entity.getStatus())
                .createAt(entity.getCreatedAt())
                .udpateAt(entity.getUpdatedAt())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static UserTokenEntity toEntity(UserToken domain) {
        UserTokenEntity entity = new UserTokenEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setAccessToken(domain.getAccessToken());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreateAt());
        entity.setUpdatedAt(domain.getUdpateAt());
        return entity;
    }
}
