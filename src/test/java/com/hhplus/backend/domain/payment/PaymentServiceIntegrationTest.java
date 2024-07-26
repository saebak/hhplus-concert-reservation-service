package com.hhplus.backend.domain.payment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicIntegerFieldUpdater;

import com.hhplus.backend.domain.concert.ConcertCommand;
import com.hhplus.backend.domain.concert.ConcertService;
import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.domain.exception.NotEnoughPointException;
import com.hhplus.backend.domain.payment.PaymentCommand.GetConcertSeatReservation;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SpringBootTest
@DisplayName("결제 통합 테스트")
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private ConcertService concertService;

    @BeforeEach
    void before() throws Exception {
        userService.chargePoint(1, 50000);
        System.out.println("사용자 포인트 충전 성공 : " + userService.getUserPoint(1).toString());
        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, 1L);
        SeatReservation seatReservation = concertService.reserveSeat(command);
        System.out.println("좌석 예약 성공 : " + seatReservation.toString());
    }

    @Test
    @DisplayName("동시에 결제버튼을 여러번 눌렀을 경우 테스트 - 낙관적락 테스트")
    public void payPointTest() throws InterruptedException {
        // given
        int threadCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기

        //AtomicLong sucessPoint = new AtomicLong();
        AtomicLong sucessCnt = new AtomicLong();        // 성공한 결과값
        AtomicLong failCount = new AtomicLong();       // 실패한 수

        for (int i=1; i<=threadCnt; i++) {
            executorService.submit(() -> {
                try {
                    PaymentCommand.GetConcertSeatReservation command = new GetConcertSeatReservation(1L, 1l, 5L, 1L, 30000);
                    Payment payment = paymentService.pay(command);
                    System.out.println("결제 성공 : " + payment.toString());
                    sucessCnt.getAndIncrement();
                } catch (NotEnoughPointException e) {
                    System.out.println(e.getMessage());
                    failCount.getAndIncrement();
                } catch (ObjectOptimisticLockingFailureException e) {
                    //log.error("[쓰레드ID : {}] ObjectOptimisticLockingFailureException :: {}", Thread.currentThread().getId() , e.getMessage());
                    System.out.println("[쓰레드ID : "+ Thread.currentThread().getId() +"] ObjectOptimisticLockingFailureException :: {"+ e.getMessage()+"}");
                    failCount.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료

        UserPoint userPoint = userService.getUserPoint(1);
        System.out.println("현재 버전 : " + userPoint.getVersion());
        //long resultPoint = sucessPoint.get();
        long success = sucessCnt.get();
        long fail = failCount.get();
        // then
        assertThat(success).isEqualTo(1);
        assertThat(fail).isEqualTo(9);
        assertThat(userPoint.getPoint()).isEqualTo(20000);
    }
}
