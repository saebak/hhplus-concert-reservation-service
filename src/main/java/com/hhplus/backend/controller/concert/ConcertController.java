package com.hhplus.backend.controller.concert;

import com.hhplus.backend.application.concert.ConcertMockFacade;
import com.hhplus.backend.controller.concert.dto.PayInput;
import com.hhplus.backend.controller.concert.dto.ReserveSeatInput;
import com.hhplus.backend.domain.concert.*;
import java.time.LocalDateTime;
import java.util.Map;
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
    public SeatReservation reserveSeat(@RequestBody ReserveSeatInput input) {
        SeatReservation reserveResult = new SeatReservation(1L, 1L, 1L, 5L, 'N', 'N', LocalDateTime.now());
        return reserveResult;
    }

    /**
     * 결제 요청
     * @param input
     * @return
     */
    @PostMapping("/pay")
    public PaymentHistory payPoint(@RequestBody PayInput input) {
        PaymentHistory payResult = new PaymentHistory(1L, 1L, 1L, 5L, 80000, "COMPLETED");
        return payResult;
    }


}
