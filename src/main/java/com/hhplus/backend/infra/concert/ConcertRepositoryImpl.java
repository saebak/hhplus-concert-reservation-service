package com.hhplus.backend.infra.concert;

import com.hhplus.backend.controller.concert.dto.ConcertInput;
import com.hhplus.backend.controller.concert.dto.ReserveSeatInput;
import com.hhplus.backend.domain.concert.ConcertRepository;
import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.domain.concert.ConcertSeat;
import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.infra.user.UserJpaRepository;
import com.hhplus.backend.infra.user.UserPointJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<ConcertSchedule> getConcertSchedules(ConcertInput input) {
        //concertScheduleJpaRepository.findAllById()
        return List.of();
    }

    @Override
    public List<ConcertSeat> getConcertSeats(ConcertInput input) {
        return List.of();
    }

    @Override
    public SeatReservation reqeustReserveSeat(ReserveSeatInput input) {
        return null;
    }
}
