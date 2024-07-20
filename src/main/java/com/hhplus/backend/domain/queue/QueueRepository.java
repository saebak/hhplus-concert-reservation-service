package com.hhplus.backend.domain.queue;

import java.util.List;
import java.util.Optional;

public interface QueueRepository {
    
    // 토큰 조회
    UserToken findToken(Long userId);

    // 토큰 발급
    UserToken saveUserToken(UserToken token);
    
    // 활성화된 토큰 갯수 조회
    Long getActiveTokenCount();
    
    // 대기 상태의 토큰 리스트 조회
    List<UserToken> getOldestWatingTokens(Long activeCount);

    List<UserToken> getOver5MinActiveTokens();
    
    // 토큰 상태 저장
    int saveAll(List<UserToken> waitingTokens);

}
