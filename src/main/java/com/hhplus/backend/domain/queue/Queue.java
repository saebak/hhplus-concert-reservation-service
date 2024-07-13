package com.hhplus.backend.domain.queue;

public class Queue {

    static final Integer MAX_ACTIVE_COUNT = 30;

    static public Long 남은액티브자리(Long activeCount) {
        long activeCount = Queue.MAX_ACTIVE_COUNT - activeTokenCount;
        return activeCount;
    }
}
