package com.hhplus.backend.domain.concert;

import java.time.LocalDateTime;

import com.hhplus.backend.domain.exception.AlreadyReservedSeatException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.coyote.BadRequestException;

@Data
@Builder
@AllArgsConstructor
public class SeatReservation {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private Long concertId;

    @Getter
    private Long scheduleId;

    @Getter
    private Long seatId;

//    @Getter
//    private ConcertSeat seat;

    @Getter
    private ConcertSchedule concertSchedule;

    @Getter
    private LocalDateTime createAt;

    public SeatReservation(Long userId, ConcertSchedule concertSchedule, ConcertSeat seat) {
        this.userId = userId;
        this.concertSchedule = concertSchedule;
        //this.seat = seat;
    }

    public void checkReserved(LocalDateTime now) throws Exception {
        // 예약 요청한지 5분 지난 좌석은 예약 가능
        if (this.createAt.isBefore(now.plusMinutes(5))) throw new AlreadyReservedSeatException("이미 예약된 좌석입니다.");
        if (this.id != null) throw new AlreadyReservedSeatException("이미 예약된 좌석입니다.");
    }

    public void checkPayment(Long userId, LocalDateTime now) throws Exception {
        // 해당 좌석이 지금 요청한 사용자가 예약한 좌석인가?
        if (this.userId != userId) throw new BadRequestException("예약자가 일치하지 않습니다.");
        // 예약 시간 검증
        if (this.createAt.isAfter(now.plusMinutes(5))) throw new Exception("예약 요청 시간이 만료되었습니다.");
    }
}
