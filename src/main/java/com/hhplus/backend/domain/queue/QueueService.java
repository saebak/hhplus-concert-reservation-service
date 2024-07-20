package com.hhplus.backend.domain.queue;

import java.util.List;
import java.util.Optional;

import com.hhplus.backend.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;

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

}
