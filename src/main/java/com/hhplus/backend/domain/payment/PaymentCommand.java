package com.hhplus.backend.domain.payment;

import lombok.Data;

public class PaymentCommand {

    @Data
    public static class GetConcertSeatReservation {
        public Long concertId;
        public Long scheduleId;
        public Long seatId;
        public Long userId;
        public int price;
    }
}
