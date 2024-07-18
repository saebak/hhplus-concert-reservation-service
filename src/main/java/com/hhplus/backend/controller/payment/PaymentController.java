package com.hhplus.backend.controller.payment;

import com.hhplus.backend.application.payment.PaymentTokenFacade;
import com.hhplus.backend.controller.payment.dto.PaymentDto;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentCommand;
import com.hhplus.backend.domain.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentTokenFacade paymentTokenFacade;

    /**
     * 결제 요청
     * @param paymentDto
     * @return Payment
     */
    @PostMapping("/pay")
    public ResponseEntity<PaymentDto.Response> payPoint(@RequestBody PaymentDto paymentDto) throws Exception {

        PaymentCommand.GetConcertSeatReservation command = new PaymentCommand.GetConcertSeatReservation();
        command.setConcertId(paymentDto.getSeatReservation().getConcertId());
        command.setScheduleId(paymentDto.getSeatReservation().getScheduleId());
        command.setSeatId(paymentDto.getSeatReservation().getSeatId());
        command.setUserId(paymentDto.getSeatReservation().getUserId());
        PaymentDto.Response response = paymentTokenFacade.payAfterExpireToken(command);

        return ResponseEntity.ok().body(response);
    }
}
