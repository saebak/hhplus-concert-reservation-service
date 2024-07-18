package com.hhplus.backend.infra.queue;

import com.hhplus.backend.infra.queue.entity.UserTokenEntity;
import com.hhplus.backend.domain.queue.UserToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueJpaRepository extends JpaRepository<UserTokenEntity, Long> {

    // 사용자 토큰 조회
    Optional<UserTokenEntity> findByUserId(long userId);

    // 활성화된 토큰 갯수 count
    Long countByStatus(String active);
    
    // 활성화 가능한 대기 토큰 목록 조회
    @Query(value = "SELECT * FROM USER_TOKEN WHERE STATUS = :status LIMIT :limit", nativeQuery = true)
    List<UserTokenEntity> findAllByStatusWithLimit(@Param("status") String status, @Param("limit") Long activeCount);
    
    // 활성화된지 5분지난 토큰 목록 조회
    @Query(value = "SELECT * FROM USER_TOKEN WHERE STATUS = :status AND DATEADD(MINUTE, 5, UPDATED_AT) < :date", nativeQuery = true)
    List<UserTokenEntity> findAllByStatusAndUpdatedAtOver5Min(@Param("status") String status, @Param("date")LocalDateTime date);
}
