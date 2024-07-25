package com.hhplus.backend.domain.concert;

import com.hhplus.backend.domain.exception.AlreadyReservedSeatException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayName("좌석 점유 및 동시성 통합 테스트")
public class ConcertServiceIntegrationTest {

    @Autowired
    private ConcertService concertService;

    @Test
    @DisplayName("동시에 5명의 사용자가 같은 좌석 예약 요청을 동시에 했을 경우, row가 0으로 5명 모두 예약에 성공한다.")
    public void reserveSeatNoLockTest() throws Exception {
        // given
        int threadCnt = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기
        long id = 1;
        AtomicInteger failCnt = new AtomicInteger();
        AtomicInteger successCnt = new AtomicInteger();

        // when
        for (int i=1; i<=threadCnt; i++) {
            long finalI = i;
            executorService.submit(() -> {
                try {
                    ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, finalI);
                    concertService.reserveSeat(command);
                    successCnt.getAndIncrement();
                } catch (AlreadyReservedSeatException e) {
                    failCnt.getAndIncrement();
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                    failCnt.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료

        // then
        List<SeatReservation> seatReservations = concertService.getReservedSeats();

        int failCount = failCnt.get();
        int successCount = successCnt.get();
        assertThat(successCount).isEqualTo(threadCnt);
        assertThat(seatReservations.size()).isEqualTo(threadCnt);

    }

    @Test
    @DisplayName("여러 스레드가 좌석 예약시 동시성 제어 테스트")
    public void reserveSeatIntegrationTest() throws Exception {
        // given
        int threadCnt = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기
        long id = 1;
        AtomicInteger failCnt = new AtomicInteger();

        for (int i=1; i<=threadCnt; i++) {
            long finalI = i;
            executorService.submit(() -> {
                try {
                    ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation();
                    command.concertId = 1L;
                    command.seatId = 5L;
                    command.scheduleId = 1L;
                    command.userId = finalI;
                    concertService.reserveSeat(command);
                } catch (AlreadyReservedSeatException e) {
                    failCnt.getAndIncrement();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료

        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation();
        command.concertId = 1L;
        command.seatId = 5L;
        command.scheduleId = 1L;
        command.userId = 1L;
        SeatReservation seatReservation = concertService.getReservedSeat(command);

        // then
        assertThat(seatReservation).isNotNull();
        assertThat(seatReservation.getUserId()).isEqualTo(command.userId);
        assertThat(seatReservation.getSeatId()).isEqualTo(command.seatId);
    }

}
