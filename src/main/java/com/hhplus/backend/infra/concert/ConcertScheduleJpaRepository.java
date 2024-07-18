package com.hhplus.backend.infra.concert;

import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.infra.concert.entity.ConcertScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {
    
    // 콘서트 스케쥴 목록 조회
    List<ConcertScheduleEntity> findAllByConcertId(Long concertId);
}
