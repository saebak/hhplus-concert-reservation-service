package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
public class ConcertSeat {

    @Getter
    private Long id;

    @Getter
    private Long scheduleId;

    @Getter
    private int seatNo;

}
