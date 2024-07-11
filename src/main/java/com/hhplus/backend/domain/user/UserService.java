package com.hhplus.backend.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // 사용자 조회
    public User getUser(long userId) {
        User user = userRepository.getUser(userId);
        return user;
    }

    // 포인트 조회
    public UserPoint getUserPoint(long userId) {
        User user = getUser(userId);
        if (user == null) {
            throw new NullPointerException();
        }
        UserPoint userPoint = userRepository.getUserPoint(userId);
        return userPoint;
    }

    // 포인트 충전/사용
    public UserPoint change(UserPoint param, String changeType) throws Exception {
        User user = getUser(param.getUserId());
        if (user == null) {
            throw new NullPointerException();
        }
        UserPoint userPoint = getUserPoint(param.getUserId());
        
        // 충전
        if ("charge".equals(changeType)) {
            userPoint.setPoint(userPoint.plusPoint(param.getPoint()));
        }

        // 사용
        if ("use".equals(changeType)) {
//            if ((userPoint.getPoint() - param.getPoint()) < 0) {
//                throw new Exception("포인트가 부족합니다.");
//            }
//            userPoint.setPoint(userPoint.getPoint()-param.getPoint());
            userPoint.setPoint(userPoint.minusPoint(param.getPoint()));
        }

        UserPoint result = userRepository.changePoint(userPoint);
        return result;
    }

}
