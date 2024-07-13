package com.hhplus.backend.infra.token;

import com.hhplus.backend.controller.token.mapper.TokenMapper;
import com.hhplus.backend.domain.queue.TokenRepository;
import com.hhplus.backend.domain.queue.UserToken;
import com.hhplus.backend.infra.token.entity.UserTokenEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private TokenJpaRepository tokenJpaRepository;

    public TokenRepositoryImpl(TokenJpaRepository tokenJpaRepository) {
        this.tokenJpaRepository = tokenJpaRepository;
    }

    @Override
    public Optional<UserToken> getToken(Long userId) {
        Optional<UserToken> userToken = tokenJpaRepository.findByUserId(userId);
        return userToken;
    }

    @Override
    public UserToken createToken(UserToken token) {
        UserTokenEntity entity = TokenMapper.toEntity(token);
        UserToken userToken = TokenMapper.toDomain(tokenJpaRepository.save(entity));
        return userToken;
    }

    @Override
    public Integer updateUserToken(Long userId, String status) {
        return null;
    }
}
