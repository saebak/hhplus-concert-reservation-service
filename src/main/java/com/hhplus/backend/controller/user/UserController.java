package com.hhplus.backend.controller.user;

import com.hhplus.backend.domain.user.PointHistory;
import com.hhplus.backend.domain.user.User;
import com.hhplus.backend.domain.user.UserPoint;
import java.util.List;
import java.util.Map;

import com.hhplus.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 포인트 조회
     * @param userId
     * @return
     */
    @GetMapping("/point/{userId}")
    public ResponseEntity<UserPoint> point(
            @PathVariable long userId
    ) {
        UserPoint userPoint = userService.getUserPoint(userId);
        return ResponseEntity.ok(userPoint);
    }

    /**
     * 포인트 충전
     * @param userId
     * @param amount
     * @return
     */
    @PatchMapping("/point/{userId}/charge")
    public ResponseEntity<UserPoint> charge(
            @PathVariable long userId,
            @RequestBody int amount
    ) throws Exception {
        UserPoint userPoint = userService.chargePoint(userId, amount);
        return ResponseEntity.ok().body(userPoint);
    }

    /**
     * 포인트 사용/충전 내역 조회 (포인트 내역부분은 미구현입니다 ㅜㅜ)
     * @param userId
     * @return
     */
    @GetMapping("/point/{userId}/histories")
    public ResponseEntity<List<PointHistory>> history(
            @PathVariable long userId
    ) {
        //List<PointHistory> historyList = userService.getUserPointHistory(userId);
        return ResponseEntity.ok().build();
    }

}
