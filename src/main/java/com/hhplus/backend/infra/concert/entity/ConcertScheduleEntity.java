package com.hhplus.backend.infra.concert.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="CONCERT_SCHEDULE")
public class ConcertScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="CONCERT_ID")
    private Long concertId;

    @Column(name="OPEN_DATE")
    private LocalDateTime openDate;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;
    
}
