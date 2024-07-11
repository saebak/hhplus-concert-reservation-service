package com.hhplus.backend.domain.user;

public interface UserRepository {
    
    // 사용자 조회
    User getUser(long userId);

    // 사용자 포인트 조회
    UserPoint getUserPoint(long userId);

    // 포인트 충전/사용
    UserPoint changePoint(UserPoint param);
}
