package com.hhplus.backend.domain.token;

public interface TokenRepository {
    
    // 토큰 발급
    UserToken saveUserToken(Long userId);
    
    // 토큰 만료
    UserToken expiredUserToken(Long userId);

}
