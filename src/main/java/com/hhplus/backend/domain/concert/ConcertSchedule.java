package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.coyote.BadRequestException;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ConcertSchedule {

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
