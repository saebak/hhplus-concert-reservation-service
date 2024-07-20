package com.hhplus.backend.infra.concert;

import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SeatReservationJpaRepository extends JpaRepository<SeatReservationEntity, Long> {

    // 점유중인 좌석인지 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SeatReservationEntity findByConcertIdAndScheduleIdAndSeatIdAndCreatedAtLessThan(Long concertId, Long scheduleId, Long seatId, LocalDateTime now);
//    @Query(value = "SELECT e FROM SeatReservationEntity e WHERE e.concertId = :concertId")
//    SeatReservationEntity findByConcertId(Long concertId);

    // 예약 요청
    SeatReservationEntity save(SeatReservationEntity seatReservationEntity);
    
    // 예약한 좌석 조회
    SeatReservationEntity findByConcertIdAndScheduleIdAndSeatIdAndUserId(Long concertId, Long scheduleId, Long seatId, Long userId);
}
