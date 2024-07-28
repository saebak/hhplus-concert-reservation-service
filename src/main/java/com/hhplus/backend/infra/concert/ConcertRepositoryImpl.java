package com.hhplus.backend.infra.concert;

import com.hhplus.backend.controller.concert.mapper.ConcertMapper;
import com.hhplus.backend.controller.concert.mapper.ConcertScheduleMapper;
import com.hhplus.backend.controller.concert.mapper.ConcertSeatMapper;
import com.hhplus.backend.controller.concert.mapper.SeatReservationMapper;
import com.hhplus.backend.domain.concert.*;
import com.hhplus.backend.infra.concert.entity.ConcertEntity;
import com.hhplus.backend.infra.concert.entity.ConcertScheduleEntity;
import com.hhplus.backend.infra.concert.entity.ConcertSeatEntity;
import com.hhplus.backend.infra.concert.entity.SeatReservationEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ConcertRepositoryImpl implements ConcertRepository {

    private ConcertJpaRepository concertJpaRepository;
    private ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private ConcertSeatJpaRepository concertSeatJpaRepository;
    private SeatReservationJpaRepository seatReservationJpaRepository;

    public ConcertRepositoryImpl(ConcertJpaRepository concertJpaRepository,
                                 ConcertScheduleJpaRepository concertScheduleJpaRepository,
                                 ConcertSeatJpaRepository concertSeatJpaRepository,
                                 SeatReservationJpaRepository seatReservationJpaRepository) {
        this.concertJpaRepository = concertJpaRepository;
        this.concertScheduleJpaRepository = concertScheduleJpaRepository;
        this.concertSeatJpaRepository = concertSeatJpaRepository;
        this.seatReservationJpaRepository = seatReservationJpaRepository;
    }

    @Override
    public List<Concert> findAll() {
        List<ConcertEntity> concertEntities = concertJpaRepository.findAll();
        List<Concert> concerts = new ArrayList<>();
        for(ConcertEntity concertEntity : concertEntities) {
            concerts.add(ConcertMapper.toDomain(concertEntity));
        }
        return concerts;
    }

    @Override
    public List<ConcertSchedule> getConcertSchedules(Long concertId) {
        List<ConcertScheduleEntity> concertScheduleEntities = concertScheduleJpaRepository.findAllByConcertId(concertId);
        List<ConcertSchedule> concertSchedules = new ArrayList<>();
        for(ConcertScheduleEntity concertScheduleEntity : concertScheduleEntities) {
            concertSchedules.add(ConcertScheduleMapper.toDomain(concertScheduleEntity));
        }
        return concertSchedules;
    }

    @Override
    public List<ConcertSeat> getConcertSeats(Long scheduleId) {
        List<ConcertSeatEntity> concertSeatEntities = concertSeatJpaRepository.findAllByScheduleId(scheduleId);
        List<ConcertSeat> concertSeats = new ArrayList<>();
        for(ConcertSeatEntity concertSeatEntity : concertSeatEntities) {
            concertSeats.add(ConcertSeatMapper.toDomain(concertSeatEntity));
        }
        return concertSeats;
    }

    @Override
    public ConcertSchedule findConcertScheduleById(Long scheduleId) {
        Optional<ConcertScheduleEntity> concertScheduleEntity = concertScheduleJpaRepository.findById(scheduleId);
        ConcertSchedule concertSchedule = ConcertScheduleMapper.toDomain(concertScheduleEntity.get());
        return concertSchedule;
    }

    @Override
    public ConcertSeat findConcertSeat(Long concertId, Long scheduleId, Long seatId) {
        ConcertSeatEntity concertSeatEntity = concertSeatJpaRepository.findByIdAndConcertIdAndScheduleId(seatId, concertId, scheduleId);
        ConcertSeat concertSeat = new ConcertSeat();
        if (concertSeatEntity != null) {
            concertSeat = ConcertSeatMapper.toDomain(concertSeatEntity);
        }
        return concertSeat;
    }

    @Override
    public SeatReservation findValidSeatReservation(Long concertId, Long scheduleId, Long seatId, LocalDateTime now) {
        SeatReservationEntity seatReservationEntity = seatReservationJpaRepository.findTop1ByConcertIdAndScheduleIdAndSeatIdOrderByCreatedAtDesc(concertId,scheduleId,seatId);
        SeatReservation seatReservation = new SeatReservation();
        if (seatReservationEntity != null) {
            seatReservation = SeatReservationMapper.toDomain(seatReservationEntity);
        }
        return seatReservation;
    }

    @Override
    public SeatReservation saveSeatReservation(SeatReservation seatReservation) {
        SeatReservationEntity seatReservationEntity = seatReservationJpaRepository.save(SeatReservationMapper.toEntity(seatReservation));
        SeatReservation result = SeatReservationMapper.toDomain(seatReservationEntity);
        return result;
    }

    @Override
    public SeatReservation getReservedSeat(Long concertId, Long scheduleId, Long seatId, Long userId) {
        SeatReservationEntity seatReservationEntity = seatReservationJpaRepository.findByConcertIdAndScheduleIdAndSeatIdAndUserId(concertId,scheduleId,seatId,userId);
        SeatReservation seatReservation = new SeatReservation();
        if (seatReservationEntity != null) {
            seatReservation = SeatReservationMapper.toDomain(seatReservationEntity);
        }
        return seatReservation;
    }

    @Override
    public List<SeatReservation> getReservedSeats() {
        List<SeatReservationEntity> seatReservationEntitys = seatReservationJpaRepository.findAll();
        List<SeatReservation> seatReservations = new ArrayList<>();
        if (!seatReservationEntitys.isEmpty()) {
            for (SeatReservationEntity seatReservationEntity : seatReservationEntitys) {
                seatReservations.add(SeatReservationMapper.toDomain(seatReservationEntity));
            }
        }
        return seatReservations;
    }

}
