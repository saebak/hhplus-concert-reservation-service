package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class ConcertCommand {

    public static class GetConcertSchedules {

        public Long concertId;
    }

    public static class GetConcertSeats {

        public Long scheduleId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetSeatReservation {

        public Long concertId;

        public Long scheduleId;

        public Long seatId;

        public Long userId;
    }
}
