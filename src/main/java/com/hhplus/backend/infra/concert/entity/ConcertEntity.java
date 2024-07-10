package com.hhplus.backend.infra.concert.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="CONCERT")
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name="CONTENT")
    private String content;

    @Column(name="PRICE")
    private int price;

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="UPDATED_AT")
    private LocalDateTime updatedAt;

}
