package com.hhplus.backend.domain.concert;

import com.hhplus.backend.domain.exception.AlreadyReservedSeatException;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
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

        Long startTime = System.currentTimeMillis();

        // when
        for (int i=1; i<=threadCnt; i++) {
            long finalI = i;
            executorService.submit(() -> {
                try {
                    ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, finalI);
                    concertService.reserveSeat(command);
                    successCnt.getAndIncrement();
                } catch (AlreadyReservedSeatException e) {
                    log.error("[쓰레드ID : {}] AlreadyReservedSeatException :: {}", Thread.currentThread().getId(), e.getMessage());
                    failCnt.getAndIncrement();
                } catch (Exception e) {
                    log.error("[쓰레드ID : {}] Exception :: {}", Thread.currentThread().getId(), e.getMessage());
                    failCnt.getAndIncrement();
                } finally {
                    Long endTime = System.currentTimeMillis();
                    log.info("소요 시간: {}", (endTime - startTime) + "ms");
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
    public void reserveSeatIntegrationPessimisticTest() throws Exception {
        // given
        int threadCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기
        AtomicInteger failCnt = new AtomicInteger();
        AtomicInteger successCnt = new AtomicInteger();

        Long startTime = System.currentTimeMillis();

        for (int i=1; i<=threadCnt; i++) {
            long finalI = i;
            executorService.submit(() -> {
                long threadStartTime = System.currentTimeMillis();
                try {
                    ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, finalI);
                    concertService.reserveSeat(command);
                    successCnt.getAndIncrement();
                } catch (AlreadyReservedSeatException e) {
                    log.error("[쓰레드ID : {}] AlreadyReservedSeatException :: {}", Thread.currentThread().getId(), e.getMessage());
                    failCnt.getAndIncrement();
                } catch (Exception e) {
                    log.error("[쓰레드ID : {}] Exception :: {}", Thread.currentThread().getId(), e.getMessage());
                    failCnt.getAndIncrement();
                } finally {
                    Long endTime = System.currentTimeMillis();
                    log.info("소요 시간: {}", (endTime - startTime) + "ms");
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

    @Test
    @DisplayName("동시에 5명의 사용자가 같은 좌석 예약 요청을 동시에 했을 경우 낙관적 사용하여 처리")
    public void reserveSeatIntegrationOptimisticTest() throws Exception {
        Thread.sleep(1000); //BeforeEach 도는 시간 기다림
        int numThreads = 10; //쓰레드 개수

        CountDownLatch latch = new CountDownLatch(numThreads); //쓰레드들을 동시 시작 및 종료를 관리하기 위한 객체
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        AtomicLong sucessCnt = new AtomicLong();        // 성공한 결과값
        AtomicLong failCount = new AtomicLong();       // 실패한 수

        Long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            long finalI = i+1;
            executorService.submit(() -> {
                try {
                    ConcertCommand.GetSeatReservation command = new ConcertCommand.GetSeatReservation(1L, 1L, 5L, finalI);
                    concertService.reserveSeat(command);
                    sucessCnt.getAndIncrement();
                } catch (ObjectOptimisticLockingFailureException e) {
                    log.error("[쓰레드ID : {}] ObjectOptimisticLockingFailureException :: {}", Thread.currentThread().getId() , e.getMessage());
                    failCount.getAndIncrement();
                } catch (Exception ex) {
                    log.error("[쓰레드ID : {}] Exception :: {}", Thread.currentThread().getId(), ex.getMessage());
                    failCount.getAndIncrement();
                } finally {
                    Long endTime = System.currentTimeMillis();
                    log.info("소요 시간: {}", (endTime - startTime) + "ms");
                    latch.countDown();
                }
            });
        }
        latch.await(); // 모든 스레드가 완료될 때까지 대기
        executorService.shutdown(); //쓰레드 풀 종료

        // then
        List<SeatReservation> seatReservations = concertService.getReservedSeats();
        long success = sucessCnt.get();
        long fail = failCount.get();
        assertThat(success).isEqualTo(1);
        assertThat(fail).isEqualTo(9);
    }

}
