package com.hhplus.backend.infra.token;

import com.hhplus.backend.infra.token.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenJpaRepository extends JpaRepository<UserTokenEntity, Long> {

}
