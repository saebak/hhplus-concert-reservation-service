package com.hhplus.backend.domain.concert;

import java.time.LocalDateTime;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    public List<Concert> getConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        return concerts;
    }

    // 콘서트 예약 가능 날짜 조회
    public List<ConcertSchedule> getConcertSchedules(ConcertCommand.GetConcertSchedules command) {
        List<ConcertSchedule> schedules = concertRepository.getConcertSchedules(command.concertId);
        return schedules;
    }
    
    // 콘서트 좌석 조회
    public List<ConcertSeat> getConcertSeats(ConcertCommand.GetConcertSeats command) {
        List<ConcertSeat> seats = concertRepository.getConcertSeats(command.scheduleId);
        // 여기서 예약된 좌석은 제외하는 로직을 추가...해야할것같은데

        return seats;
    }

    // 좌석 예약 요청
    @Transactional
    public SeatReservation reserveSeat(ConcertCommand.GetSeatReservation command) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        ConcertSchedule concertSchedule = concertRepository.findConcertScheduleById(command.scheduleId);
        concertSchedule.checkReservationCondition();    // 콘서트 스케쥴 검증

        ConcertSeat seat = concertRepository.findConcertSeat(command.concertId, command.scheduleId, command.seatId);
        if(seat == null) throw new RuntimeException("없는 좌석입니다.");   // 존재하는 좌석인지 검증

        // 다른 사람예약이 점유중인지 확인해야함.
        SeatReservation seatReservation = concertRepository.findValidSeatReservation(command.concertId, command.scheduleId, command.seatId, now);
        if (seatReservation != null ) seatReservation.checkReserved(now);

        SeatReservation newReservation = new SeatReservation(command.userId, concertSchedule, seat);
        return concertRepository.saveSeatReservation(newReservation);
    }
}