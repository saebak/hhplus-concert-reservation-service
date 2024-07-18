package com.hhplus.backend.infra.payment;

import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.infra.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
    
    // 결제 내역 조회
    List<PaymentEntity> findAllById(long userId);
}
