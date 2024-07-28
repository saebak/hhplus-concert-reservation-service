package com.hhplus.backend.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PaymentCommand {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetConcertSeatReservation {
        public Long concertId;
        public Long scheduleId;
        public Long seatId;
        public Long userId;
        public int price;
    }
}
