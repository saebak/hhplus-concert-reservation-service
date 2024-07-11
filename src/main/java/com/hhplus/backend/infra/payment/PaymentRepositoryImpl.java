package com.hhplus.backend.infra.payment;

import com.hhplus.backend.controller.payment.dto.PayInput;
import com.hhplus.backend.controller.user.mapper.UserMapper;
import com.hhplus.backend.controller.user.mapper.UserPointMapper;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentRepository;
import com.hhplus.backend.domain.user.User;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserRepository;
import com.hhplus.backend.infra.user.UserJpaRepository;
import com.hhplus.backend.infra.user.UserPointJpaRepository;
import com.hhplus.backend.infra.user.entity.UserEntity;
import com.hhplus.backend.infra.user.entity.UserPointEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private PaymentJpaRepository paymentJpaRepository;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    @Override
    public Payment pay(PayInput input) {
        return null;
    }
}
