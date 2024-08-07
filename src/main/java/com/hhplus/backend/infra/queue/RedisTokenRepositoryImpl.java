package com.hhplus.backend.infra.queue;

import com.hhplus.backend.domain.queue.RedisTokenRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisTokenRepositoryImpl implements RedisTokenRepository {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean setWaitingToken(long userId, long now) {
        //String value = uuid + ":" + userId;
        boolean result = redisTemplate.opsForZSet().add("WAITING_TOKEN", userId, now);
        return result;
    }

    @Override
    public Long getWaitingNo(long userId) {
        long result = redisTemplate.opsForZSet().rank("WAITING_TOKEN", userId);
        return result;
    }

    @Override
    public Set<Object> getTransferWaitingToken(long start, long end) {
        Set<Object> result = redisTemplate.opsForZSet().range("WAITING_TOKEN", start, end);
        return result;
    }

    @Override
    public void popWaitingToken(long start, long end) {
        redisTemplate.opsForZSet().removeRange("WAITING_TOKEN", start, end);
    }

    @Override
    public void setActiveToken(long userId, long now) {
        // ACCESS 토큰 발급 후 10분뒤 만료
        redisTemplate.opsForValue().set("ACTIVE_TOKEN:"+userId, userId, 600, TimeUnit.SECONDS);
    }

    @Override
    public long getActiveToken(long userId) {
        long activeToken = -1;
        activeToken =  (long) redisTemplate.opsForValue().get("ACTIVE_TOKEN:"+userId);
        return activeToken;
    }
}
