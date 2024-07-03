package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class SeatReservation {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private Long concertId;

    @Getter
    private Long seatId;

    @Getter
    private char payYn;
}
