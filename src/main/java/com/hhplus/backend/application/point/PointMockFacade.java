package com.hhplus.backend.application.point;


import com.hhplus.backend.domain.concert.Concert;
import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.domain.concert.ConcertSeat;
import com.hhplus.backend.domain.point.PointHistory;
import com.hhplus.backend.domain.point.UserPoint;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class PointMockFacade {

    private UserPoint userPoint = userPointMock();
    private List<PointHistory> histories = pointHistoryMock(1);

    // 유저 포인트 더미 데이터
    private UserPoint userPointMock() {
        UserPoint userPoint = new UserPoint(1L, 1L, 30000);
        return userPoint;
    }

    // 포인트 사용/충전 내역 더미 데이터
    private List<PointHistory> pointHistoryMock(long userId) {
        List<PointHistory> histories = new ArrayList<>();
        histories.add(new PointHistory(1L, userId, 20000, "CHARGE", LocalDateTime.now()));
        histories.add(new PointHistory(2L, userId, 10000, "CHARGE", LocalDateTime.now()));

        return histories;
    }
}
