package com.hhplus.backend.controller.concert;

import com.hhplus.backend.controller.concert.dto.ConcertReserveSeatDto;
import com.hhplus.backend.domain.concert.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private static final Logger log = LoggerFactory.getLogger(ConcertController.class);

    @Autowired
    private ConcertService concertService;

    /**
     * 콘서트 리스트 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Concert>> getConcerts() {
        List<Concert> concerts = concertService.getConcerts();
        return ResponseEntity.ok().body(concerts);
    }

    /**
     * 해당 콘서트의 예약 가능한 날짜 조회
     * @param concertId
     * @return
     */
    @GetMapping("/{concertId}/schedule")
    public ResponseEntity<List<ConcertSchedule>> getSchedules(@PathVariable Long concertId) {
        ConcertCommand.GetConcertSchedules command = new ConcertCommand.GetConcertSchedules();
        command.concertId = concertId;

        List<ConcertSchedule> schedules = concertService.getConcertSchedules(command);
        return ResponseEntity.ok().body(schedules);
    }

    /**
     * 선택한 콘서트 스케쥴의 예약 가능 좌석 조회
     * @param scheduleId
     * @return
     */
    @GetMapping("/{scheduleId}/seat")
    public ResponseEntity<List<ConcertSeat>> getSeats(@PathVariable Long scheduleId) {
        ConcertCommand.GetConcertSeats command = new ConcertCommand.GetConcertSeats();
        command.scheduleId = scheduleId;

        List<ConcertSeat> seats = concertService.getConcertSeats(command);
        return ResponseEntity.ok().body(seats);
    }

    /**
     * 좌석 예약 요청
     * @param request
     * @return
     */
    @PostMapping("/reservation")
    public ResponseEntity<SeatReservation> reserveSeat(@RequestBody ConcertReserveSeatDto.Request request) throws Exception {
        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation();
        command.concertId = request.getConcertId();
        command.scheduleId = request.getScheduleId();
        command.seatId = request.getSeatId();
        command.userId = request.getUserId();
        SeatReservation seatReservation = concertService.reserveSeat(command);

        return ResponseEntity.ok().body(seatReservation);
    }


}
