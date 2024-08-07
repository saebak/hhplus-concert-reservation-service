package com.hhplus.backend.domain.concert;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private CacheManager cacheManager;

    private final ObjectMapper objectMapper;

    @Autowired
    public ConcertService(ObjectMapper objectMapper) {
        // ObjectMapper가 Spring에 의해 주입됩니다.
        this.objectMapper = objectMapper;
    }
    
    // 콘서트 목록 조회
    @Cacheable(value = "concerts", cacheManager = "cacheManager")
    public List<Concert> getConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        return concerts;
    }

    // 콘서트 예약 가능 날짜 조회
    @Cacheable(key = "#command.toString()", value = "schedules", cacheManager = "cacheManager")
    public List<ConcertSchedule> getConcertSchedules(ConcertCommand.GetConcertSchedules command) {
        List<ConcertSchedule> schedules = concertRepository.getConcertSchedules(command.concertId);
        return schedules;
    }
    
    // 콘서트 좌석 조회
    @Cacheable(key = "#command.toString()", value = "seats", cacheManager = "cacheManager")
    public List<ConcertSeat> getConcertSeats(ConcertCommand.GetConcertSeats command) {
        List<ConcertSeat> seats = concertRepository.getConcertSeats(command.scheduleId);
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
            seatReservation.setSeat(seat);
            seatReservation.setConcertSchedule(concertSchedule);
            concertRepository.saveSeatReservation(seatReservation);
        }

        SeatReservation newReservation = new SeatReservation(command.userId, concertSchedule, seat);
        var result = concertRepository.saveSeatReservation(newReservation);
        ////////////////////////////////////
        return result;
    }

    // 예약중인 좌석 조회
    @Transactional
    public SeatReservation getReservedSeat(ConcertCommand.GetSeatReservation command) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        // 내가 예약한 좌석 조회
        SeatReservation seatReservation = concertRepository.getReservedSeat(command.concertId, command.scheduleId, command.seatId, command.userId);
        return seatReservation;
    }

    // 예약된 좌석 전체 조회
    @Transactional
    public List<SeatReservation> getReservedSeats() throws Exception {
        List<SeatReservation> seatReservation = concertRepository.getReservedSeats();
        return seatReservation;
    }
}