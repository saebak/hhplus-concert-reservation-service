package com.hhplus.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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

    public int plusPoint(int addPoint) {
        return point + addPoint;
    }

    public int minusPoint(int usePoint) throws Exception {
        int resultPoint = point - usePoint;

        if (resultPoint < 0) {
            throw new Exception("포인트가 부족합니다.");
        }

        return resultPoint;
    }
}
