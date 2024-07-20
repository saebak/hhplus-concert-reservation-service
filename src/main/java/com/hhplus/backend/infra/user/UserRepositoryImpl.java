package com.hhplus.backend.infra.user;

import com.hhplus.backend.controller.user.mapper.UserMapper;
import com.hhplus.backend.controller.user.mapper.UserPointMapper;
import com.hhplus.backend.domain.user.User;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserRepository;
import com.hhplus.backend.infra.queue.QueueJpaRepository;
import com.hhplus.backend.infra.user.entity.UserEntity;
import com.hhplus.backend.infra.user.entity.UserPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;
    private UserPointJpaRepository userPointJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserPointJpaRepository userPointJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.userPointJpaRepository = userPointJpaRepository;
    }

    @Override
    public User getUser(long userId) {
        Optional<UserEntity> user = userJpaRepository.findById(userId);
        UserEntity userEntity = user.get();
        User result = UserMapper.toDomain(userEntity);
        return result;
    }

    @Override
    public UserPoint getUserPoint(long userId) {
        UserPointEntity userPoint = userPointJpaRepository.findByUserId(userId);
        UserPoint result = UserPointMapper.toDomain(userPoint);
        return result;
    }

    @Override
    public UserPoint updateUserPoint(UserPoint userPoint) {
        UserPointEntity entity = UserPointMapper.toEntity(userPoint);
        UserPointEntity resultPoint = userPointJpaRepository.save(entity);
        UserPoint result = UserPointMapper.toDomain(resultPoint);
        return result;
    }
}
