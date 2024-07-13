package com.hhplus.backend.domain.concert;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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

    @Getter
    private String status;

    @Getter
    private LocalDateTime createAt;
}
