package com.hhplus.backend.controller.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ConcertReserveSeatDto {

    public class Request {
        // 조회 날짜
        private LocalDate searchDate;

        // 콘서트 아이디
        private Long concertId;

        // 콘서트 스케쥴 아이디
        private Long scheduleId;
    }

    public class Response {}

}
