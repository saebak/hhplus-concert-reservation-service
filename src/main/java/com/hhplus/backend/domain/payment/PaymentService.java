package com.hhplus.backend.domain.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hhplus.backend.domain.concert.ConcertRepository;
import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.domain.queue.RedisTokenRepository;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserRepository;
import com.hhplus.backend.support.enums.EventType;
import com.hhplus.backend.support.event.PaymentEventPublisher;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTokenRepository redisTokenRepository;

    @Autowired
    private PaymentEventPublisher paymentEventPublisher;

    // 결제 요청 - kafka outbox pattern 추가
    @Transactional
    public void payKafkaOutboxPattern(PaymentCommand.GetConcertSeatReservation command) throws Exception {    // 기존 pay메서드 그대로 남기려구 네이밍만 다르게 해서 작성했습니다
        LocalDateTime now = LocalDateTime.now();
        Payment info = new Payment();

        // 결제가 유효한 예약인지 검증
        SeatReservation seatReservation = concertRepository.findValidSeatReservation(command.concertId, command.scheduleId, command.seatId, now);
        seatReservation.checkPayment(command.userId, now);

        info.setSeatReservation(seatReservation);

        // 유저 포인트 조회 및 차감
        UserPoint userPoint = userRepository.getUserPoint(command.userId);
        userPoint.minusPoint(command.price);

        // 결제 정보 저장
        UserPoint usePoint = userRepository.updateUserPoint(userPoint);
        info.setPrice(command.price);
        info.setStatus("PAYMENT");
        info.setCreatedAt(LocalDateTime.now());
        Payment payment = paymentRepository.savePayment(info);

        PaymentSuccessEvent paymentSuccessEvent = new PaymentSuccessEvent(payment.getId(),payment.getUserId(),payment.getPrice(),payment.getStatus(),payment.getCreatedAt());

        // outbox 저장
        // type은 나중에 enum으로 리팩토링
        PaymentOutbox outbox = new PaymentOutbox(payment.getId(), "paymentEvent",  EventType.RECEIVED.getResultMessage(), paymentSuccessEvent);
        paymentRepository.savePaymentOutbox(outbox);

        // 결제 정보 전송 이벤트
        paymentEventPublisher.publish(paymentSuccessEvent);
        
        // 결제 완료 후 active 토큰 삭제
        redisTokenRepository.popActiveToken(payment.getUserId());
    }

    // 결제 요청 - 결제정보저장 event 추가
    @Transactional
    public void payEvent(PaymentCommand.GetConcertSeatReservation command) throws Exception {    // 기존 pay메서드 그대로 남기려구 네이밍을 다르게 했습니다.
        LocalDateTime now = LocalDateTime.now();
        Payment info = new Payment();

        // 결제가 유효한 예약인지 검증
        SeatReservation seatReservation = concertRepository.findValidSeatReservation(command.concertId, command.scheduleId, command.seatId, now);
        seatReservation.checkPayment(command.userId, now);

        info.setSeatReservation(seatReservation);

        // 유저 포인트 조회 및 차감
        UserPoint userPoint = userRepository.getUserPoint(command.userId);
        userPoint.minusPoint(command.price);

        // 결제 정보 저장
        UserPoint usePoint = userRepository.updateUserPoint(userPoint);
        info.setPrice(command.price);
        info.setStatus("PAYMENT");
        info.setCreatedAt(LocalDateTime.now());
        Payment payment = paymentRepository.savePayment(info);

        // 결제 정보 전송 이벤트
        paymentEventPublisher.success(new PaymentSuccessEvent(payment.getId(),payment.getUserId(),payment.getPrice(),payment.getStatus(),payment.getCreatedAt()));

        // 결제 완료 후 active 토큰 삭제
        redisTokenRepository.popActiveToken(payment.getUserId());
    }
    
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
    
    // outbox 에서 status가 init인 정보 list 조회
    public List<PaymentOutbox> getPaymentOutboxsByStatus(String aggregateType, String status) throws JsonProcessingException {
        List<PaymentOutbox> outboxList = paymentRepository.getPaymentOutboxsStatusInit(aggregateType, status);
        return outboxList;
    }

    // outbox list 조회
    public List<PaymentOutbox> getPaymentOutboxs() throws JsonProcessingException {
        List<PaymentOutbox> outboxList = paymentRepository.getPaymentOutboxs();
        return outboxList;
    }

    // 결제 취소 로직 필요할듯...?

}
