package com.hhplus.backend.domain.user;

import com.hhplus.backend.domain.exception.NotEnoughPointException;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserPoint {

    @Version
    private long version;

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private int point;

    @Getter
    public LocalDateTime createdAt;

    @Getter
    public LocalDateTime updatedAt;

    public UserPoint (Long id, Long userId, int point) {
        this.id = id;
        this.userId = userId;
        this.point = point;
    }

    public void plusPoint(int addPoint) {
        this.point = this.point + addPoint;
    }

    public void minusPoint(int usePoint) throws Exception {
        if (this.point-usePoint < 0) {
            throw new NotEnoughPointException("포인트가 부족합니다.");
        }
        this.point = this.point-usePoint;
    }
}
