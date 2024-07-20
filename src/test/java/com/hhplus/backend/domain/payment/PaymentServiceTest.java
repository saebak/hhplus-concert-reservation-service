package com.hhplus.backend.domain.payment;

import com.hhplus.backend.domain.concert.ConcertRepository;
import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.domain.concert.ConcertSeat;
import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("결제 테스트")
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ConcertRepository concertRepository;
    @Mock
    private UserRepository userRepository;
    private ConcertSchedule basicSchedule;
    private ConcertSeat basicSeat;
    private SeatReservation basicReservations;
    private UserPoint basicUserPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 기본 객체 세팅
        long id = 1;
        long userId = 1;
        long concertId = 1;
        long scheduleId = 3;
        long seatId = 4;
        int seatNo = 4;
        int point = 50000;
        this.basicSchedule = new ConcertSchedule(id, concertId, LocalDateTime.now().minusDays(1));
        this.basicSeat = new ConcertSeat(seatId, scheduleId, seatNo);
        this.basicReservations = new SeatReservation(userId, basicSchedule, basicSeat);
        basicReservations.setCreatedAt(LocalDateTime.now());
        this.basicUserPoint = new UserPoint(id, userId, point);
    }

    @Test
    @DisplayName("결제 요청 테스트")
    public void payTest() throws Exception {
        // given
        PaymentCommand.GetConcertSeatReservation command = new PaymentCommand.GetConcertSeatReservation();
        command.concertId = 1L;
        command.scheduleId = 3L;
        command.seatId = 4L;
        command.userId = 1L;
        command.price = 10000;
        given(concertRepository.findValidSeatReservation(anyLong(),anyLong(),anyLong(),any())).willReturn(basicReservations);
        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);
        basicUserPoint.minusPoint(command.price);
        given(userRepository.updateUserPoint(any())).willReturn(basicUserPoint);
        Payment payment = new Payment();
        payment.setSeatReservation(basicReservations);
        payment.setPrice(command.price);
        payment.setStatus("PAYMENT");
        given(paymentRepository.savePayment(any())).willReturn(payment);

        // when
        Payment result = paymentService.pay(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("PAYMENT");
        assertThat(result.getPrice()).isEqualTo(command.price);
        assertThat(result.getScheduleId()).isEqualTo(payment.getScheduleId());
        assertThat(result.getSeatId()).isEqualTo(payment.getSeatId());
    }

    @Test
    @DisplayName("결제 요청 테스트 - 좌석 예약이 유효하지 않은 경우")
    public void reservationSeatOverTimeTest() {
        // given
    }

    @Test
    @DisplayName("결제 요청 테스트 - 포인트가 부족할 경우")
    public void notEnoughPointTest() {
        // given
    }

}
