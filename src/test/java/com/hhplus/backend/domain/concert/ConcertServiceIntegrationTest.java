package com.hhplus.backend.domain.concert;

import com.hhplus.backend.domain.exception.AlreadyReservedSeatException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    // 동시성 제어
    @Test
    @DisplayName("여러 스레드가 좌석 예약시 동시성 제어 테스트")
    public void reserveSeatIntegrationTest() throws Exception {
        // given
        int threadCnt = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기
        long id = 1;
        AtomicInteger failCnt = new AtomicInteger();
        AtomicInteger successCnt = new AtomicInteger();

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
                    successCnt.getAndIncrement();
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

        System.out.println("!!!!!!!!!!!!!! : " + successCnt);

        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation();
        command.concertId = 1L;
        command.seatId = 5L;
        command.scheduleId = 1L;
        command.userId = 1L;
        SeatReservation seatReservation = concertService.getReservedSeat(command);

        // then
        assertThat(seatReservation).isNotNull();
        assertThat(failCnt).isEqualTo(1);
        assertThat(seatReservation.getUserId()).isEqualTo(command.userId);
    }

}
