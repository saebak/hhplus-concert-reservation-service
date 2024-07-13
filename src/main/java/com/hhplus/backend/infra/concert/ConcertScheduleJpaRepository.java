package com.hhplus.backend.infra.concert;

import com.hhplus.backend.infra.concert.entity.ConcertScheduleEntity;
import com.hhplus.backend.infra.user.entity.UserPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {

}
