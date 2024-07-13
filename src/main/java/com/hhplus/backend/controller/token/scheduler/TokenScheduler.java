package com.hhplus.backend.controller.token.scheduler;

import com.hhplus.backend.domain.queue.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenScheduler {

    private final TokenService tokenService;

    @Scheduled(fixedDelay = 60000)
    public void udpateTokenStauts () {

    }
}
