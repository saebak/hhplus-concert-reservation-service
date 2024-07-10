package com.hhplus.backend.infra.concert.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="CONCERT_SEAT")
public class ConcertSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="SCHEDULE_ID")
    private Long scheduleId;

    @Column(name="SEAT_NO")
    private int seatNo;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;
}
