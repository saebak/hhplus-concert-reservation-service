package com.hhplus.backend.controller.payment.dto;

import com.hhplus.backend.domain.concert.SeatReservation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayInput {
    
    // 예약 정보
    private SeatReservation seatReservation;
    
    // 유저 아이디
    private Long userId;
    
    // 가격
    private int price;
}
