package com.hhplus.backend.domain.payment;

import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOutbox {

    private Long id;

    private Long aggregateId;

    private String aggregateType;

    private String eventType;

    private PaymentSuccessEvent payload;

    private LocalDateTime createdAt;

    private String status;

    private LocalDateTime publishedAt;

    private Long retryCount;

    public PaymentOutbox(Long aggregateId, String aggregateType, String eventType, PaymentSuccessEvent payload) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.payload = payload;
        this.status = "INIT";
        this.retryCount = 0L;
    }

    public void publish() {
        this.status = "PUBLISHED";
        this.publishedAt = LocalDateTime.now();
    }

    public void plusRetryCount() {
        this.retryCount++;
    }
}
