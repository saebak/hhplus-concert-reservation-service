package com.hhplus.backend.domain;

import com.hhplus.backend.domain.queue.TokenRepository;
import com.hhplus.backend.domain.queue.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@DisplayName("사용자 토큰 조회 및 상태변경 테스트")
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private TokenRepository tokenRepository;

    @Test
    @DisplayName("사용자 토큰 조회 테스트")
    public void getTokenTest() {

    }

    @Test
    @DisplayName("토큰 발급 테스트")
    public void issueTokenTest() {

    }

    @Test
    @DisplayName("토큰 상태 변경(활성화/만료) 테스트")
    public void updateTokenTest() {

    }
}
