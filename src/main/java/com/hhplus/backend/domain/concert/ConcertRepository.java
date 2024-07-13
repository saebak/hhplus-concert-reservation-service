package com.hhplus.backend.domain.concert;

import com.hhplus.backend.controller.concert.dto.ConcertInput;
import com.hhplus.backend.controller.concert.dto.ReserveSeatInput;

import com.hhplus.backend.domain.concert.ConcertCommand.GetSeatReservation;
import java.util.List;

public interface ConcertRepository {

    // 콘서트 예약 가능 날짜 조회
    List<ConcertSchedule> getConcertSchedules(Long concertId);
    
    // 예약 가능 좌석 조회
    List<ConcertSeat> getConcertSeats(Long concertScheduleId);
    
    // 좌석 예약 요청
    SeatReservation saveSeatReservation(SeatReservation seatReservation);
    
    // 예약 좌석 조회
    SeatReservation getSeatReservation(GetSeatReservation command);
}
