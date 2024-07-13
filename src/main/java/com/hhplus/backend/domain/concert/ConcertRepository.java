package com.hhplus.backend.domain.concert;

import com.hhplus.backend.controller.concert.dto.ConcertInput;
import com.hhplus.backend.controller.concert.dto.ReserveSeatInput;

import java.util.List;

public interface ConcertRepository {

    // 콘서트 예약 가능 날짜 조회
    List<ConcertSchedule> getConcertSchedules(ConcertInput input);
    
    // 예약 가능 좌석 조회
    List<ConcertSeat> getConcertSeats(ConcertInput input);
    
    // 좌석 예약 요청
    SeatReservation reqeustReserveSeat(ReserveSeatInput input);


}
