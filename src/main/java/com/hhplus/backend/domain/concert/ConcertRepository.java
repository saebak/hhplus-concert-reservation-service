package com.hhplus.backend.domain.concert;

import com.hhplus.backend.domain.concert.ConcertCommand.GetSeatReservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository {
    
    // 콘서트 목록 조회
    List<Concert> findAll();

    // 콘서트 예약 가능 날짜 조회
    List<ConcertSchedule> getConcertSchedules(Long concertId);
    
    // 예약 가능 좌석 조회
    List<ConcertSeat> getConcertSeats(Long scheduleId);
    
    // 해당 콘서트 스케쥴이 존재하는지 조회
    ConcertSchedule findConcertScheduleById(Long scheduleId);
    
    // 해당 좌석 존재하는지 조회
    ConcertSeat findConcertSeat(Long concertId, Long scheduleId, Long seatId);
    
    // 다른 사람이 점유중인 좌석인지 조회
    SeatReservation findValidSeatReservation(Long concertId, Long scheduleId, Long seatId, LocalDateTime now);
    
    // 좌석 예약 요청
    SeatReservation saveSeatReservation(SeatReservation seatReservation);
    
    // 예약한 좌석 조회
    SeatReservation getReservedSeat(Long concertId, Long scheduleId, Long seatId, Long userId);

    List<SeatReservation> getReservedSeats();
}
