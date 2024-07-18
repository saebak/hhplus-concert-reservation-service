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

    private final QueueService queueService;

    @Scheduled(fixedDelay = 10000)
    public void runUpdateActivateTokens () {
        queueService.activateTokens();
    }

    @Scheduled(fixedDelay = 10000)
    public void runUpdateExpireTokens () {
        queueService.expireTokens();
    }
}
