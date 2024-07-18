package com.hhplus.backend.infra.user;

import com.hhplus.backend.infra.user.entity.UserPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointJpaRepository extends JpaRepository<UserPointEntity, Long> {
    
    // 사용자 포인트 조회
    UserPointEntity findByUserId(long userId);

}
