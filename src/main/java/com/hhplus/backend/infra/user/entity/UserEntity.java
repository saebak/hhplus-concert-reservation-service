package com.hhplus.backend.infra.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    // 사용자 정보 ~

    @CreatedDate
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;

}
