package com.hhplus.backend.domain.payment;

import com.hhplus.backend.controller.payment.dto.PayInput;
import com.hhplus.backend.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentService (PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    // 결제
    public Payment pay(PayInput input) {
        Payment payment = paymentRepository.pay(input);
        return payment;
    }

}
