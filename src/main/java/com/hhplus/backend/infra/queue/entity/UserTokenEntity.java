package com.hhplus.backend.infra.queue.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="USER_TOKEN")
public class UserTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name="ACCESS_TOKEN")
    private String accessToken;

    @Column(name="STATUS")
    private String status;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;
}
