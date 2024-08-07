package com.hhplus.backend.domain.queue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private RedisTokenRepository redisTokenRepository;

    /**
     * 토큰 조회
     * @param userId
     * @return
     */
    public UserToken getToken(long userId) {
        UserToken token = queueRepository.findToken(userId);
        return token;
    }

    /**
     * 토큰 발행
     * @param userId
     * @return
     */
    @Transactional
    public UserToken issueToken(long userId) {
        UserToken alreadyToken = queueRepository.findToken(userId);

        // 이미 토큰이 존재하면 만료시키고 새로 발급 (새로고침하면 다시 처음부터이듯이....)
        if (alreadyToken.getId() != null) {
            alreadyToken.expire();
            queueRepository.saveUserToken(alreadyToken);
        }
        // 신규 토큰 생성
        UserToken newToken = new UserToken();
        newToken.setNewToken(userId);

        // 토큰 발급
        UserToken resultToken = queueRepository.saveUserToken(newToken);
        return resultToken;
    }

    @Transactional
    public void activateTokens() {
        // 활성화된 토큰수
        Long activeTokenCount = queueRepository.getActiveTokenCount();
        Long activeCount = Queue.availableActiveSeats(activeTokenCount);
        List<UserToken> waitingTokens = queueRepository.getOldestWatingTokens(activeCount);
        waitingTokens.stream().forEach(token -> token.active());
        int result = queueRepository.saveAll(waitingTokens);
    }

    @Transactional
    public UserToken expireToken(Long userId) {
        UserToken token = queueRepository.findToken(userId);
        token.expire();
        UserToken expiredToken = queueRepository.saveUserToken(token);
        return expiredToken;
    }

    @Transactional
    public void expireTokens() {
        // 활성화된지 5분이 지난 토큰 리스트 조회
        List<UserToken> activeTokens = queueRepository.getOver5MinActiveTokens();
        activeTokens.stream().forEach(token -> token.expire());
        int result = queueRepository.saveAll(activeTokens);
    }

    // 토큰 발행
    public long issueTokenRedis(long userId) throws Exception {
        long now = parseDate(LocalDateTime.now());
        //String uuid = UUID.randomUUID().toString();
        boolean result = redisTokenRepository.setWaitingToken(userId, now);   // 토큰 발급
        if (!result) {
            throw new Exception("토큰 발급도중 오류가 발생했습니다.");
        }
        return redisTokenRepository.getWaitingNo(userId);    // 대기번호 반환
    }

    // access token 조회
    public long getActiveToken(long userId) {
        long activeToken = redisTokenRepository.getActiveToken(userId);
        return activeToken;
    }
    
    // waiting token 조회
    public long getWaitingToken(long userId) {
        long waitingNo = redisTokenRepository.getWaitingNo(userId);
        return waitingNo;
    }

    // 순차적으로 waiting token active token 으로 변환
    public void transferToken(long start, long end) {
        Set<Object> values =  redisTokenRepository.getTransferWaitingToken(start, end); // 대기->활성화시킬 토큰 값 조회 
        if (values != null) {
            for (Object value : values) {
                long now = parseDate(LocalDateTime.now());
                //String uuid = UUID.randomUUID().toString();
                redisTokenRepository.setActiveToken((Long)value, now);    // active 토큰 등록
            }
        }
        redisTokenRepository.popWaitingToken(start,end);     // 활성화 시킨 토큰 삭제
    }

    private long parseDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return Long.parseLong(dateTime.format(formatter));
    }
}
