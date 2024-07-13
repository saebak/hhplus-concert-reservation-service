package com.hhplus.backend.domain.concert;

import com.hhplus.backend.controller.concert.dto.ConcertInput;
import com.hhplus.backend.controller.concert.dto.ReserveSeatInput;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    public ConcertService (ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    // 콘서트 예약 가능 날짜 조회
    public List<ConcertSchedule> getConcertSchedules(ConcertInput input) {
        List<ConcertSchedule> schedules = concertRepository.getConcertSchedules(input);
        return schedules;
    }
    
    // 콘서트 좌석 조회
    public List<ConcertSeat> getConcertSeats(ConcertInput input) {
        List<ConcertSeat> seats = concertRepository.getConcertSeats(input);
        return seats;
    }

    // 좌석 예약 요청
    public SeatReservation reqeustReserveSeat(ReserveSeatInput input) {
        SeatReservation reserve = concertRepository.reqeustReserveSeat(input);
        return reserve;
    }

}
