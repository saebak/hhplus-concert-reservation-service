package com.hhplus.backend.infra.queue;

import com.hhplus.backend.controller.queue.mapper.TokenMapper;
import com.hhplus.backend.domain.queue.QueueRepository;
import com.hhplus.backend.domain.queue.UserToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hhplus.backend.infra.queue.entity.UserTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public class QueueRepositoryImpl implements QueueRepository {

    private QueueJpaRepository queueJpaRepository;

    public QueueRepositoryImpl(QueueJpaRepository queueJpaRepository) {
        this.queueJpaRepository = queueJpaRepository;
    }


    @Override
    public UserToken findToken(Long userId) {
        Optional<UserTokenEntity> userTokenEntity = queueJpaRepository.findByUserId(userId);
        UserToken userToken = new UserToken();
        if (userTokenEntity.isPresent()) {
            userToken = TokenMapper.toDomain(userTokenEntity.get());
        }
        return userToken;
    }

    @Override
    public UserToken saveUserToken(UserToken token) {
        UserTokenEntity userTokenEntity = queueJpaRepository.save(TokenMapper.toEntity(token));
        UserToken userToken = TokenMapper.toDomain(userTokenEntity);
        return userToken;
    }

    @Override
    public Long getActiveTokenCount() {
        return queueJpaRepository.countByStatus("ACTIVE");
    }

    @Override
    public List<UserToken> getOldestWatingTokens(Long activeCount) {
        List<UserTokenEntity> userTokenEntities = queueJpaRepository.findAllByStatusWithLimit("WAIT", activeCount);
        List<UserToken> userTokens = new ArrayList<>();
        for(UserTokenEntity userTokenEntity : userTokenEntities) {
            userTokens.add(TokenMapper.toDomain(userTokenEntity));
        }
        return userTokens;
    }

    @Override
    public List<UserToken> getOver5MinActiveTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<UserTokenEntity> userTokenEntities = queueJpaRepository.findAllByStatusAndUpdatedAtOver5Min("ACTIVE", now);
        List<UserToken> userTokens = new ArrayList<>();
        for(UserTokenEntity userTokenEntity : userTokenEntities) {
            userTokens.add(TokenMapper.toDomain(userTokenEntity));
        }
        return userTokens;
    }

    @Override
    public int saveAll(List<UserToken> waitingTokens) {
        List<UserTokenEntity> userTokenEntities = new ArrayList<>();
        for (UserToken userToken : waitingTokens) {
            userTokenEntities.add(TokenMapper.toEntity(userToken));
        }
        List<UserTokenEntity> result = queueJpaRepository.saveAll(userTokenEntities);
        return result.size();
    }
}
