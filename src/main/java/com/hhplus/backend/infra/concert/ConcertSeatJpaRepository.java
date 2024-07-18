package com.hhplus.backend.infra.concert;

import com.hhplus.backend.domain.concert.ConcertSeat;
import com.hhplus.backend.infra.concert.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
    
    // 좌석 조회
    List<ConcertSeatEntity> findAllByScheduleId(Long scheduleId);

    // 해당 좌석이 존재하는가 조회
    ConcertSeatEntity findByIdAndConcertIdAndScheduleId(Long seatId, Long concertId, Long scheduleId);
}
