package com.hhplus.backend.infra.concert;

import com.hhplus.backend.infra.concert.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {

}
