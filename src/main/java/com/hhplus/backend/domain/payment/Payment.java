package com.hhplus.backend.domain.payment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class Payment {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private Long concertId;

    @Getter
    private String concertTitle;

    @Getter
    private Long scheduleId;

    @Getter
    private Long seatId;

    @Getter
    private int price;

    @Getter
    private String status;
}
