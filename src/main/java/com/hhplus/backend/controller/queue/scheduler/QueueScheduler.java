package com.hhplus.backend.controller.queue.scheduler;

import com.hhplus.backend.domain.queue.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class QueueScheduler {

    private final Long ACTIVE_TOKEN_COUNT = 5L;

    private final QueueService queueService;
    
    // 3분마다 5명씩 대기열 통과
    @Scheduled(fixedDelay = 300000)
    public void runUpdateActivateTokens () {
        queueService.transferToken(0, ACTIVE_TOKEN_COUNT);
    }

}
