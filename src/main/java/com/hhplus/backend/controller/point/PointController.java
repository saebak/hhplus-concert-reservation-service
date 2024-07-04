package com.hhplus.backend.controller.point;

import com.hhplus.backend.application.point.PointMockFacade;
import com.hhplus.backend.domain.point.PointHistory;
import com.hhplus.backend.domain.point.UserPoint;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    private final PointMockFacade pointMockFacade;

    /**
     * 포인트 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public UserPoint point(
            @PathVariable long userId
    ) {
        UserPoint userPoint = pointMockFacade.getUserPoint();
        return userPoint;
    }

    /**
     * 포인트 사용/충전 내역 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/histories")
    public List<PointHistory> history(
            @PathVariable long userId
    ) {
        List<PointHistory> historyList = pointMockFacade.getHistories();
        return historyList;
    }

    /**
     * 포인트 충전
     * @param userId
     * @param input
     * @return
     */
    @PatchMapping("/{userId}/charge")
    public UserPoint charge(
            @PathVariable long userId,
            @RequestBody Map<String, Long> input
    ) {

        UserPoint userPoint = pointMockFacade.getUserPoint();
        return userPoint;
    }

}
