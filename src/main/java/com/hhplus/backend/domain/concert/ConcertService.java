package com.hhplus.backend.domain.concert;

import java.time.LocalDateTime;

import com.hhplus.backend.domain.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
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
        if(seat.getId() == null) throw new NotFoundException("없는 좌석입니다.");   // 존재하는 좌석인지 검증

        // 다른 사람예약이 점유중인지 확인해야함.
        SeatReservation seatReservation = concertRepository.findValidSeatReservation(command.concertId, command.scheduleId, command.seatId, now);
        if (seatReservation.getId() != null ) {
            seatReservation.checkReserved(now);   // 예약한지 5분이 지나지 않은 예약인지 확인
        }

        SeatReservation newReservation = new SeatReservation(command.userId, concertSchedule, seat);
        var result = concertRepository.saveSeatReservation(newReservation);
        System.out.println("예약성공 : id =  " + result.getId() + ", userId = " + result.getUserId());
        return result;
    }

    // 예약중인 좌석 조회
    public SeatReservation getReservedSeat(ConcertCommand.GetSeatReservation command) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        // 내가 예약한 좌석 조회
        SeatReservation seatReservation = concertRepository.getReservedSeat(command.concertId, command.scheduleId, command.seatId, command.userId);
        return seatReservation;
    }

    // 예약된 좌석 전체 조회
    public List<SeatReservation> getReservedSeats() throws Exception {
        List<SeatReservation> seatReservation = concertRepository.getReservedSeats();
        return seatReservation;
    }
}