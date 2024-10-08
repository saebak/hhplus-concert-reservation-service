package com.hhplus.backend.infra.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="PAYMENT")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="CONCERT_ID")
    private Long concertId;

    @Column(name="CONCERT_TITLE")
    private String concertTitle;

    @Column(name="SCHEDULE_ID")
    private Long scheduleId;

    @Column(name="SEAT_ID")
    private Long seatId;

    @Column(name="PRICE")
    private int price;

    @Column(name="STATUS")
    private String status;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;
}
