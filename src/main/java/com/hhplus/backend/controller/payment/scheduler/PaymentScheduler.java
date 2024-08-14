package com.hhplus.backend.controller.payment.scheduler;

import com.hhplus.backend.domain.payment.PaymentService;
import com.hhplus.backend.domain.queue.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentScheduler {

    private final PaymentService paymentService;

    @Scheduled(fixedRate = 5000)
    public void runUpdatePaymentEvent() {
//        paymentService.findAllByStatus(INIT).forEach {
//            if(it.createdAt < now() - 5minutes) {
//                slackApiClient.sendMessageToDeveloper();
//                kafkaPublisher.publishOrderInfo();
//            }
//        }
    }
}
