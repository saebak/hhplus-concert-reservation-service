package com.hhplus.backend.infra.token;

import com.hhplus.backend.domain.token.TokenRepository;
import com.hhplus.backend.domain.token.UserToken;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private TokenJpaRepository tokenJpaRepository;

    public TokenRepositoryImpl(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public UserToken saveUserToken(Long userId) {
        return null;
    }

    @Override
    public UserToken expiredUserToken(Long userId) {
        return null;
    }
}
