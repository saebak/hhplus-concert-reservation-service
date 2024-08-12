package com.hhplus.backend.support.event;

import com.hhplus.backend.domain.user.UserService;
import com.hhplus.backend.support.client.PaymentMockApiClient;
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

    public PaymentEventListener(PaymentMockApiClient paymentApiClient, UserService userService) {
        this.paymentApiClient = paymentApiClient;
        this.userService = userService;
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentSuccessHandler(PaymentSuccessEvent event) throws Exception {
        PaymentPayload payload = new PaymentPayload(event);
        try {
            paymentApiClient.sendPaymentInfo(payload);
        } catch (Exception e) {
            log.error("결제정보 전송중 에러가 발생했습니다 : {} ", e.getMessage());
            // 보상 트랜잭션
            // 차감 포인트 재충전
            userService.chargePoint(payload.getUserId(),payload.getPrice());
            // 예약상태도 다시 돌리는 로직 필요
            // concertService.rallbackReservationStatus();
        }

    }
}