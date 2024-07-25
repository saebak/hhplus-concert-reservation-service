package com.hhplus.backend.domain.user;

import com.hhplus.backend.domain.exception.NotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 사용자 조회
    public User getUser(long userId) {
        User user = userRepository.getUser(userId);
        return user;
    }

    // 포인트 조회
    public UserPoint getUserPoint(long userId) {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
        UserPoint userPoint = userRepository.getUserPoint(userId);
        return userPoint;
    }

    // 포인트 충전
    @Transactional
    public UserPoint chargePoint(long userId, int amount) throws Exception {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
        UserPoint userPoint = getUserPoint(userId);
        userPoint.plusPoint(amount);

        UserPoint resultPoint = userRepository.updateUserPoint(userPoint);
        return resultPoint;
    }

    // 포인트 사용
    @Transactional
    public UserPoint usePoint(long userId, int usePoint) throws Exception {
        User user = userRepository.getUser(userId);
        if (user == null) {
            throw new NotFoundException("사용자가 존재하지 않습니다.");
        }
        UserPoint userPoint = getUserPoint(userId);
        userPoint.minusPoint(usePoint);

        UserPoint resultPoint = userRepository.updateUserPoint(userPoint);
        return resultPoint;
    }

}
