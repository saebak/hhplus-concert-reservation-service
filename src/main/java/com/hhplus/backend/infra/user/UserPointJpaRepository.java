package com.hhplus.backend.infra.user;

import com.hhplus.backend.infra.user.entity.UserPointEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface UserPointJpaRepository extends JpaRepository<UserPointEntity, Long> {
    
    // 사용자 포인트 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    UserPointEntity findByUserId(long userId);

}
