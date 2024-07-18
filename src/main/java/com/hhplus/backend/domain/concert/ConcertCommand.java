package com.hhplus.backend.domain.concert;

public class ConcertCommand {

    public static class GetConcertSchedules {

        public Long concertId;
    }

    public static class GetConcertSeats {

        public Long scheduleId;
    }

    public static class GetSeatReservation {

        public Long concertId;

        public Long scheduleId;

        public Long seatId;

        public Long userId;
    }
}
