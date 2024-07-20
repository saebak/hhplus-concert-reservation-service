package com.hhplus.backend.domain.concert;

import com.hhplus.backend.domain.exception.AlreadyReservedSeatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("콘서트 조회 및 좌석 예약 테스트")
public class ConcertServiceTest {

    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;
    private List<ConcertSchedule> basicSchedule;
    private List<ConcertSeat> basicSeat;
    private List<SeatReservation> basicReservations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 기본 객체 세팅
        long concertId = 1;
        basicSchedule = new ArrayList<>();
        basicSeat = new ArrayList<>();
        for (long i=0; i<5; i++) {
            this.basicSchedule.add(new ConcertSchedule(i, concertId, LocalDateTime.now().minusDays(i)));
        }
        long scheduleId = 1;
        for (int i=0; i<50; i++) {
            this.basicSeat.add(new ConcertSeat((long) i, scheduleId, i));
        }
    }

    @Test
    @DisplayName("콘서트 예약 가능 날짜 조회 테스트")
    public void getConcertSchedulesTest() {
        // given
        ConcertCommand.GetConcertSchedules command = new ConcertCommand.GetConcertSchedules();
        command.concertId = 2L;
        given(concertRepository.getConcertSchedules(anyLong())).willReturn(basicSchedule);

        // when
        List<ConcertSchedule> result = concertService.getConcertSchedules(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(basicSchedule.size());
    }

    @Test
    @DisplayName("콘서트 좌석 조회 테스트")
    public void getConcertSeatsTest() {
        // given
        ConcertCommand.GetConcertSeats command = new ConcertCommand.GetConcertSeats();
        command.scheduleId = 3L;
        given(concertRepository.getConcertSeats(anyLong())).willReturn(basicSeat);

        // when
        List<ConcertSeat> result = concertService.getConcertSeats(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(basicSeat.size());

    }

    @Test
    @DisplayName("좌석 예약 요청 테스트")
    public void reserveSeatTest() throws Exception {
        // given
        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation();
        command.concertId = 1L;
        command.scheduleId = 3L;
        command.seatId = 5L;
        command.userId = 1L;
        given(concertRepository.findConcertScheduleById(anyLong())).willReturn(basicSchedule.get(2));
        given(concertRepository.findConcertSeat(anyLong(),anyLong(),anyLong())).willReturn(basicSeat.get(4));
        given(concertRepository.findValidSeatReservation(anyLong(),anyLong(),anyLong(),any())).willReturn(null);
        SeatReservation newSeatReservation = new SeatReservation(command.userId, basicSchedule.get(2), basicSeat.get(4));
        given(concertRepository.saveSeatReservation(any())).willReturn(newSeatReservation);

        // when
        SeatReservation result = concertService.reserveSeat(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(newSeatReservation);
    }

    @Test
    @DisplayName("좌석 예약 요청 테스트 - 이미 다른 사람이 좌석 점유중인 경우")
    public void alreadyReserveSeatTest() throws Exception {
        // given
        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation();
        command.concertId = 1L;
        command.scheduleId = 3L;
        command.seatId = 5L;
        command.userId = 1L;
        given(concertRepository.findConcertScheduleById(anyLong())).willReturn(basicSchedule.get(2));
        given(concertRepository.findConcertSeat(anyLong(),anyLong(),anyLong())).willReturn(basicSeat.get(4));
        SeatReservation alreadySeatReservation = new SeatReservation(command.userId, basicSchedule.get(2), basicSeat.get(4));
        alreadySeatReservation.setCreatedAt(LocalDateTime.now());
        given(concertRepository.findValidSeatReservation(anyLong(),anyLong(),anyLong(),any())).willReturn(alreadySeatReservation);
        SeatReservation newSeatReservation = new SeatReservation(command.userId, basicSchedule.get(2), basicSeat.get(4));
        given(concertRepository.saveSeatReservation(any())).willReturn(newSeatReservation);

        // when
        // then
        assertThatExceptionOfType(AlreadyReservedSeatException.class)
                .isThrownBy(()->{
                    concertService.reserveSeat(command);
                }).withMessage("이미 예약된 좌석입니다.");
    }
    
}
