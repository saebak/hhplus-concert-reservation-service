package com.hhplus.backend.infra.payment;

import com.hhplus.backend.infra.payment.entity.PaymentEntity;
import com.hhplus.backend.infra.payment.entity.PaymentOutboxEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, Long> {
    

}
