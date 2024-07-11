package com.hhplus.backend.infra.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="POINT_HISTORY")
public class PointHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="POINT")
    private int point;

    @Column(name="STATUS")
    private String status;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

}
