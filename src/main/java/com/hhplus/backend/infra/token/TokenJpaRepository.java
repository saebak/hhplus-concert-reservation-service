package com.hhplus.backend.infra.token;

import com.hhplus.backend.domain.queue.UserToken;
import com.hhplus.backend.infra.token.entity.UserTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<UserTokenEntity, Long> {

    // 사용자 포인트 조회
    public Optional<UserToken> findByUserId(long userId);
}
