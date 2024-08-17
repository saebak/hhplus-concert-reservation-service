package com.hhplus.backend.infra.payment;

import com.hhplus.backend.infra.payment.entity.PaymentOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, Long> {


    PaymentOutboxEntity findByAggregateIdAndAggregateType(Long paymentId, String topic);

    @Query("SELECT e FROM PaymentOutboxEntity e WHERE e.aggregateType = :aggregateType AND e.status = :status AND e.createdAt < :currentTime")
    List<PaymentOutboxEntity> findAllByAggregateTypeAndStatus(@Param("aggregateType") String aggregateType,
                                                              @Param("status") String status,
                                                              @Param("currentTime") LocalDateTime currentTime);
}
