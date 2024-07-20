package com.hhplus.backend.domain.payment;

import com.hhplus.backend.domain.concert.ConcertRepository;
import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private UserRepository userRepository;
    
    // 결제 요청
    @Transactional
    public Payment pay(PaymentCommand.GetConcertSeatReservation command) throws Exception {

        LocalDateTime now = LocalDateTime.now();
        Payment info = new Payment();

        // 결제 목록에 있는 좌석인지 확인 로직 필요 ^^,,
        // paymentRepository.findById();

        // 결제 가능한거야? 아직 유효해?
        SeatReservation seatReservation = concertRepository.findValidSeatReservation(command.concertId, command.scheduleId, command.seatId, now);
        seatReservation.checkPayment(command.userId, now);

        info.setSeatReservation(seatReservation);

        // 포인트 검사하고 차감시켜
        UserPoint userPoint = userRepository.getUserPoint(command.userId);
        userPoint.minusPoint(command.price);

        // 오키 진짜 결제 간다
        UserPoint usePoint = userRepository.updateUserPoint(userPoint);
        info.setPrice(command.price);
        info.setStatus("PAYMENT");
        info.setCreatedAt(LocalDateTime.now());

        Payment payment = paymentRepository.savePayment(info);
        return payment;
    }

    // 결제 취소 로직 필요할듯...?

}
