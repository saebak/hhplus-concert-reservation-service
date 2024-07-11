package com.hhplus.backend.infra.concert;

import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatReservationJpaRepository extends JpaRepository<SeatReservationEntity, Long> {

}
