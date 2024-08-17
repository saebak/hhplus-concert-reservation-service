package com.hhplus.backend.support.event;

import com.hhplus.backend.domain.user.UserService;
import com.hhplus.backend.support.client.PaymentMockApiClient;
import com.hhplus.backend.support.event.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventListener {

    private final PaymentMockApiClient paymentApiClient;
    private final UserService userService;

    private final KafkaProducer kafkaProducer;

    public PaymentEventListener(PaymentMockApiClient paymentApiClient, UserService userService,
            KafkaProducer kafkaProducer) {
        this.paymentApiClient = paymentApiClient;
        this.userService = userService;
        this.kafkaProducer = kafkaProducer;
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentSuccessEvent event) throws Exception {

        // kafka에 message 등록
        kafkaProducer.publishPaymentInfo(event);

        // 기존 event listener 방식
//        PaymentPayload payload = new PaymentPayload(event);
//        try {
//            paymentApiClient.sendPaymentInfo(payload);
//        } catch (Exception e) {
//            log.error("결제정보 전송중 에러가 발생했습니다 : {} ", e.getMessage());
//            // 보상 트랜잭션
//            // 차감 포인트 재충전
//            userService.chargePoint(payload.getUserId(),payload.getPrice());
//            // 예약상태도 다시 돌리는 로직 필요
//            // concertService.rallbackReservationStatus();
//        }

    }
}