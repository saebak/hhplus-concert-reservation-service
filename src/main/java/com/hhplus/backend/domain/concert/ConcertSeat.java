package com.hhplus.backend.domain.concert;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeat implements Serializable {

    private static final long serialVersionUID = 324032423049234L;

    @Getter
    private Long id;

    @Getter
    private Long scheduleId;

    @Getter
    private int seatNo;
}
