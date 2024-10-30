package com.hhplus.backend.domain.Queue;

import com.hhplus.backend.domain.concert.ConcertCommand;
import com.hhplus.backend.domain.concert.ConcertService;
import com.hhplus.backend.domain.concert.SeatReservation;
import com.hhplus.backend.domain.exception.NotEnoughPointException;
import com.hhplus.backend.domain.payment.Payment;
import com.hhplus.backend.domain.payment.PaymentCommand.GetConcertSeatReservation;
import com.hhplus.backend.domain.payment.PaymentService;
import com.hhplus.backend.domain.queue.QueueService;
import com.hhplus.backend.domain.user.UserPoint;
import com.hhplus.backend.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
@DisplayName("레디스 사용 토큰 발급 통합테스트")
public class QueueServiceIntegrationTest {

    @Autowired
    private QueueService queueService;
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("레디스 토큰발급 성공 테스트")
    public void issueTokenTest() throws Exception {
        // given
        long userId = 1;

        // when
        for (long i = 1; i <10; i++) {
            queueService.issueTokenRedis(i);
        }

        // then
    }
}
