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
    @DisplayName("동시에 5명의 사용자가 같은 좌석 예약 요청을 동시에 했을 경우 비관적락 사용하여 처리")
    public void reserveSeatIntegrationTest() throws Exception {
        // given
        int threadCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기
        AtomicInteger failCnt = new AtomicInteger();
        AtomicInteger successCnt = new AtomicInteger();

        // 각 스레드별로 걸린 시간을 저장할 배열
        long[] threadTimes = new long[threadCnt];

        for (int i=1; i<=threadCnt; i++) {
            long finalI = i;
            executorService.submit(() -> {
                long threadStartTime = System.currentTimeMillis();
                try {
                    ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, finalI);
                    concertService.reserveSeat(command);
                    successCnt.getAndIncrement();
                } catch (AlreadyReservedSeatException e) {
                    System.out.println(e.getMessage());
                    failCnt.getAndIncrement();
                } catch (Exception e) {
                    //throw new RuntimeException(e);
                    System.out.println(e.getMessage());
                    failCnt.getAndIncrement();
                } finally {
                    long threadEndTime = System.currentTimeMillis();
                    threadTimes[(int) finalI-1] = threadEndTime - threadStartTime;
                    System.out.println("걸린 시간 : " + threadTimes[(int) finalI -1]);
                    latch.countDown();
                }
            });
        }
        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료

        ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, 1L);
        SeatReservation seatReservation = concertService.getReservedSeat(command);

        // then
        int failCount = failCnt.get();
        int successCount = successCnt.get();
        assertThat(successCount).isEqualTo(1);
        assertThat(failCount).isEqualTo(9);
    }

}
