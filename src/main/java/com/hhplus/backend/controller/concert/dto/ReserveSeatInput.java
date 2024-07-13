package com.hhplus.backend.controller.concert.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReserveSeatInput {

    // 유저 아이디
    private Long userId;

    // 콘서트 아이디
    private Long concertId;

    // 콘서트 스케쥴 아이디
    private Long scheduleId;
    
    // 좌석 아이디
    private Long seatId;
}
