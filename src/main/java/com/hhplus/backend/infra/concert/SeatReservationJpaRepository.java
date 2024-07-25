package com.hhplus.backend.infra.concert;

import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface SeatReservationJpaRepository extends JpaRepository<SeatReservationEntity, Long> {

    // 점유중인 좌석인지 조회
//    @Lock(LockModeType.OPTIMISTIC)
    // 비관적 락 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SeatReservationEntity findTop1ByConcertIdAndScheduleIdAndSeatIdOrderByCreatedAtDesc(Long concertId, Long scheduleId, Long seatId);

    // 예약 요청
    SeatReservationEntity save(SeatReservationEntity seatReservationEntity);
    
    // 예약한 좌석 조회
    SeatReservationEntity findByConcertIdAndScheduleIdAndSeatIdAndUserId(Long concertId, Long scheduleId, Long seatId, Long userId);
}
