package com.hhplus.backend.infra.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Data
@Table(name="PAYMENT_OUTBOX")
public class PaymentOutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AGGREGATE_ID")
    private Long aggregateId;

    @Column(name = "AGGREGATE_TYPE")
    private String aggregateType;

    @Column(name = "EVENT_TYPE")
    private String eventType;

    @Column(name = "PAYLOAD")
    private String payload;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "status")
    private String status;

    @Column(name = "PUBLISHED_AT")
    private LocalDateTime publishedAt;

    @Column(name = "RETRY_COUNT")
    private Long retryCount;
}
