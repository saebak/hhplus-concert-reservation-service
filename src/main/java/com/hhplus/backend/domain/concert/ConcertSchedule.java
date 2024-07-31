package com.hhplus.backend.domain.concert;

import lombok.*;
import org.apache.coyote.BadRequestException;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSchedule implements Serializable {

    private static final long serialVersionUID = 23490324893240L;

    @Getter
    private Long id;

    @Getter
    private Long concertId;

    @Getter
    private LocalDateTime openDate;

    public void checkReservationCondition() throws BadRequestException {
        LocalDateTime now = LocalDateTime.now();
        if (openDate.isAfter(now)) throw new BadRequestException("예약 가능한 날짜가 아닙니다.");
    }
}
