package com.hhplus.backend.controller.concert;

import com.hhplus.backend.application.concert.ConcertMockFacade;
import com.hhplus.backend.controller.concert.dto.ConcertDto;
import com.hhplus.backend.controller.concert.dto.ConcertReserveSeatDto;
import com.hhplus.backend.domain.concert.*;
import com.hhplus.backend.domain.queue.UserToken;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertController {

    private static final Logger log = LoggerFactory.getLogger(ConcertController.class);

    private final ConcertMockFacade concertMockFacade;

    /**
     * 콘서트 리스트 조회
     * @return
     */
    @GetMapping("/list")
    public List<Concert> getConcerts() {
        List<Concert> concerts = concertMockFacade.getConcerts();
        return concerts;
    }

    /**
     * 해당 콘서트의 예약 가능한 날짜 조회
     * @param concertId
     * @return
     */
    @GetMapping("/{concertId}/schedule")
    public List<ConcertSchedule> getSchedules(@PathVariable Long concertId) {
        List<ConcertSchedule> schedules = concertMockFacade.getSchedules();
        return schedules;
    }

    /**
     * 선택한 콘서트 스케쥴의 예약 가능 좌석 조회
     * @param scheduleId
     * @return
     */
    @GetMapping("/{scheduleId}/seat")
    public List<ConcertSeat> getSeats(@PathVariable Long scheduleId) {
        List<ConcertSeat> seats = concertMockFacade.getSeats();
        return seats;
    }

    /**
     * 사용자 대기열 토큰 발급 요청
     * @param input
     * @return
     */
    @PostMapping("/token/create")
    public UserToken createToken(@RequestBody Map<String, Long> input) {
        UserToken userToken = concertMockFacade.getTokenMock();
        return userToken;
    }

    /**
     * 좌석 예약 요청
     * @param input
     * @return
     */
    @PostMapping("/reservation")
    public ConcertReserveSeatDto.Response reserveSeat(@RequestBody ConcertReserveSeatDto.Request requset) {
        SeatReservation reserveResult = new SeatReservation(1L, 1L, 1L, 5L, 1L, "WAIT", LocalDateTime.now());
        return reserveResult;
    }


}
