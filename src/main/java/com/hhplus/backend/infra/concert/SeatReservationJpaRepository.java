package com.hhplus.backend.infra.concert;

import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;

public interface SeatReservationJpaRepository extends JpaRepository<SeatReservationEntity, Long> {

    // 점유중인 좌석인지 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SeatReservationEntity findByConcertIdAndScheduleIdAndSeatIdAndCreatedAtLessThan(Long concertId, Long scheduleId, Long seatId, LocalDateTime now);
    
    // 예약 요청
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SeatReservationEntity save(SeatReservationEntity seatReservationEntity);
}
