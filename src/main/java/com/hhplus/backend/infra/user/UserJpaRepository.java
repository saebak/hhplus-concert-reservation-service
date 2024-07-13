package com.hhplus.backend.infra.user;

import com.hhplus.backend.infra.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

}
