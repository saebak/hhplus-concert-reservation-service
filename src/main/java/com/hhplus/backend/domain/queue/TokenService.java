package com.hhplus.backend.domain.queue;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueueService {

    @Autowired
    private TokenRepository tokenRepository;

    public TokenService (TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Optional<UserToken> getToken(long userId) {
        Optional<UserToken> token = tokenRepository.getToken(userId);
        return token;
    }

    public UserToken issueToken(long userId) {

        Optional<UserToken> alredayToken = tokenRepository.findToken(userId);

        // 이미 토큰이 존재하면 만료시키고 새로 발급 (새로고침하면 다시 처음부터이듯이....)
        if (!token.isEmpty()) {
            token.expire();
        }
        UserToken newToken = new UserToken();

        // 토큰 발급
        tokenRepository.saveToken(alredayToken);
        tokenRepository.saveToken(newToken);
        return userToken;
    }

    @Transactional
    public void activateTokens() {
        // 활성화된 토큰수
        Long activeTokenCount = queueRepository.getActiveTokenCount();
        long activeCount = Queue.남은액티브자리(activeTokenCount);
        List<UserToken> waitingTokens = tokenRepository.getOldestWatingTokens(activeCount);
        waitingTokens.steam().forEach(token -> token.active());
        tokenRepository.saveAll(waitingTokens);
    }

    @Transactional
    public UserToken expireTokens() {
        List<UserToken> activeTokens = tokenRepository.getOver5MinActiveTokens(activeCount);
        activeTokens.steam().forEach(token -> token.expier());

    }

}
