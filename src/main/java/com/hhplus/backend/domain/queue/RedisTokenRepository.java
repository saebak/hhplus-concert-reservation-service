package com.hhplus.backend.domain.queue;

import java.util.Set;

public interface RedisTokenRepository {

    Boolean setWaitingToken(long userId, long now);

    Long getWaitingNo(long userId);

    Set<Object> getTransferWaitingToken(long start, long end);

    void popWaitingToken(long start, long end);

    void setActiveToken(long userId, long now);

    long getActiveToken(long userId);
}
