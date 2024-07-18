package com.hhplus.backend.controller.payment.dto;

import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.queue.UserToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDto {

    // 예약 정보
    private SeatReservation seatReservation;
    
    // 유저 아이디
    private Long userId;

    @Data
    public static class Response {
        private Payment payment;
        private UserToken userToken;
    }

}
