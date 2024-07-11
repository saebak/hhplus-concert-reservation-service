package com.hhplus.backend.domain.concert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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


}
