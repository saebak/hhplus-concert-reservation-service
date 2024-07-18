package com.hhplus.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.coyote.BadRequestException;

@Data
@Builder
@AllArgsConstructor
public class UserPoint {

    @Getter
    private Long id;

    @Getter
    private Long userId;

    @Getter
    private int point;

    public void plusPoint(int addPoint) {
        this.point = this.point + addPoint;
    }

    public void minusPoint(int usePoint) throws Exception {
        if (this.point-usePoint < 0) {
            throw new BadRequestException("포인트가 부족합니다.");
        }
        this.point = this.point-usePoint;
    }
}
