package com.hhplus.backend.controller.user.mapper;

import com.hhplus.backend.domain.user.User;
import com.hhplus.backend.infra.user.entity.UserEntity;

public class UserMapper {

    /**
     * Entity에서 domain으로 변환
     * @param entity
     * @return
     */
    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }


    /**
     * domain에서 entity로 변환
     * @param domain
     * @return
     */
    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }
}
