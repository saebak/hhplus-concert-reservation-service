package com.hhplus.backend.domain.concert;

import com.hhplus.backend.domain.exception.AlreadyReservedSeatException;
import jakarta.transaction.Transactional;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
@DisplayName("콘서트 정보 조회 캐싱 사용 통합 테스트")
public class ConcertServiceCacheIntegrationTest {

    @Autowired
    private ConcertService concertService;

    // 동시성 제어
    @Test
    @DisplayName("콘서트 정보 조회 캐싱 사용 통합 테스트")
    public void getDataCachingTest() throws Exception {
        // given
        int threadCnt = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기
        AtomicInteger failCnt = new AtomicInteger();
        AtomicInteger successCnt = new AtomicInteger();

        Long startTime = System.currentTimeMillis();

        // when
        for (int i=1; i<=threadCnt; i++) {
            executorService.submit(() -> {
                try {
                    List<Concert> concerts = concertService.getConcerts();
                    log.info(concerts.toString());
                    ConcertCommand.GetConcertSchedules command = new ConcertCommand.GetConcertSchedules();
                    command.concertId = 1L;
                    List<ConcertSchedule> schedules = concertService.getConcertSchedules(command);
                    log.info(schedules.toString());
                    ConcertCommand.GetConcertSeats command2 = new ConcertCommand.GetConcertSeats();
                    command2.scheduleId = 1L;
                    List<ConcertSeat> seats = concertService.getConcertSeats(command2);
                    log.info(seats.toString());
                    successCnt.getAndIncrement();
                } catch (Exception e) {
                    log.error("[쓰레드ID : {}] Exception :: {}", Thread.currentThread().getId(), e.getMessage());
                    failCnt.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }

        Long endTime = System.currentTimeMillis();
        log.info("소요 시간: {}", (endTime - startTime) + "ms");

        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료
    }

}
