package com.hhplus.backend.domain.queue;

import java.util.Optional;

public interface TokenRepository {

    Optional<UserToken> getToken(Long userId);
    
    // 토큰 발급
    UserToken createToken(UserToken token);
    
    // 토큰 활성화/만료
    Integer updateUserToken(Long userId, String status);

}
