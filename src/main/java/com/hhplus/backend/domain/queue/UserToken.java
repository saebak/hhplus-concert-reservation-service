package com.hhplus.backend.domain.queue;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private String accessToken;

    @Getter
    private String status;

    @Getter
    private LocalDateTime createAt;

    @Getter
    private LocalDateTime udpateAt;
    
    // 토큰이 활성화 상태인지 확인
    public boolean isActive() {
        return this.status.equals("ACTIVE");
    }
    
    // 토큰 만료상태로 변경
    public void expire () {
        this.status = "EXPIRED";
    }

    // 토큰 활성화상태로 변경
    public void active() {
        this.status = "ACTIVE";
    }

    // 신규 토큰 생성
    public void setNewToken(long userId) {
        this.userId = userId;
        this.accessToken = UUID.randomUUID().toString();
        this.status = "WAIT";
        this.createAt = LocalDateTime.now();
        this.udpateAt = LocalDateTime.now();
    }
}
