package com.hhplus.backend.controller.payment;

import com.hhplus.backend.controller.payment.dto.PayInput;
import com.hhplus.backend.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    /**
     * 결제 요청
     * @param input
     * @return
     */
    @PostMapping("/pay")
    public Payment payPoint(@RequestBody PayInput input) {
        Payment payResult = new Payment(1L, 1L, 1L, "데스노트", 1L, 10L, 50000, "COMPLETED");
        return payResult;
    }
}
