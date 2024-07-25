package com.hhplus.backend.application.payment;


import com.hhplus.backend.controller.payment.dto.PaymentDto;
import com.hhplus.backend.domain.payment.PaymentCommand;
import com.hhplus.backend.domain.payment.PaymentService;
import com.hhplus.backend.domain.queue.QueueService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@AllArgsConstructor
public class PaymentTokenFacade {

    private PaymentService paymentService;
    private QueueService queueService;

    // 결제요청 + 토큰 만료
    public PaymentDto.Response payAfterExpireToken(PaymentCommand.GetConcertSeatReservation command) throws Exception {
        PaymentDto.Response response = new PaymentDto.Response();
        response.setPayment(paymentService.pay(command));
        response.setUserToken(queueService.expireToken(command.userId));
        return response;
    }

}
