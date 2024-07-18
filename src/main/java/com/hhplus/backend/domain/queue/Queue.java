package com.hhplus.backend.domain.queue;

public class Queue {

    static final Integer MAX_ACTIVE_COUNT = 30;
    
    // 남는 토큰 활성화 자리 구하기
    static public Long availableActiveSeats(Long activeTokenCount) {
        long activeCount = Queue.MAX_ACTIVE_COUNT - activeTokenCount;
        return activeCount;
    }

}
