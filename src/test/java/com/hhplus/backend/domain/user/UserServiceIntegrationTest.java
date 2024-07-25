package com.hhplus.backend.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("좌석 점유 및 동시성 통합 테스트")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("동시에 포인트 충전을 여러번 눌렀을 경우 테스트")
    public void chargePointTest() throws InterruptedException {
        // given
        int threadCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기

        AtomicLong sucessPoint = new AtomicLong();
        AtomicLong sucessCnt = new AtomicLong();        // 성공한 결과값
        AtomicLong failCount = new AtomicLong();       // 실패한 수

        for (int i=1; i<=threadCnt; i++) {
            executorService.submit(() -> {      // 각 thread가 chargePoint를 호출
                try {
                    long id = 1;
                    int addPoint = 10000;
                    UserPoint userPoint = userService.chargePoint(id, addPoint);        // 10000p씩 충전
                    System.out.println("충전 성공 : " + userPoint.getPoint());
                    sucessPoint.set(userPoint.getPoint());                           // 충전후 결과값
                    sucessCnt.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료

        long resultPoint = sucessPoint.get();
        long success = sucessCnt.get();
        long fail = failCount.get();
        // then
        assertThat(resultPoint).isEqualTo(10000);
        assertThat(success).isEqualTo(1);
        assertThat(fail).isEqualTo(9);
    }

    @Test
    @DisplayName("동시에 포인트 충전을 여러번 눌렀을 경우 테스트 - 비관적락 처리")
    public void chargePointPessimisticTest() throws InterruptedException {
        // given
        int threadCnt = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);  // 고정된 스레드 풀을 생성하여 여러 스레드에서 작업을 실행
        CountDownLatch latch = new CountDownLatch(threadCnt);                       // 모든 스레드가 작업 완료할때 까지 대기

        AtomicLong sucessPoint = new AtomicLong();
        AtomicLong sucessCnt = new AtomicLong();        // 성공한 결과값
        AtomicLong failCount = new AtomicLong();       // 실패한 수

        for (int i=1; i<=threadCnt; i++) {
            executorService.submit(() -> {      // 각 thread가 chargePoint를 호출
                try {
                    long id = 1;
                    int addPoint = 10000;
                    UserPoint userPoint = userService.chargePoint(id, addPoint);        // 10000p씩 충전
                    System.out.println("충전 성공 : " + userPoint.getPoint());
                    sucessPoint.set(userPoint.getPoint());                           // 충전후 결과값
                    sucessCnt.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();                  // 모든 thread가 종료될대까지 기다림
        executorService.shutdown();     // thread풀 종료

        long resultPoint = sucessPoint.get();
        long success = sucessCnt.get();
        long fail = failCount.get();
        // then
        assertThat(resultPoint).isEqualTo(100000);
        assertThat(success).isEqualTo(10);
        assertThat(fail).isEqualTo(0);
    }

//    @Test
//    @DisplayName("포인트 사용 테스트")
//    public void usePointTest() throws Exception {
//        // given
//        long userId = 1;
//        int usePoint = 1000;
//        UserPoint newUserPoint = new UserPoint(basicUserPoint.getId(), userId, basicUserPoint.getPoint()-usePoint);
//        given(userRepository.getUser(anyLong())).willReturn(basicUser);
//        given(userRepository.getUserPoint(anyLong())).willReturn(basicUserPoint);
//        given(userRepository.updateUserPoint(any())).willReturn(newUserPoint);
//
//        // when
//        UserPoint userPoint = userService.usePoint(userId, usePoint);
//
//        // then
//        assertThat(userPoint).isNotNull();
//        assertThat(userPoint.getId()).isEqualTo(basicUserPoint.getId());
//        assertThat(userPoint.getUserId()).isEqualTo(basicUserPoint.getUserId());
//        assertThat(userPoint.getPoint()).isEqualTo(basicUserPoint.getPoint());
//    }
    
}
