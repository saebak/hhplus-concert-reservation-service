package com.hhplus.backend.domain.Queue;

import com.hhplus.backend.domain.queue.Queue;
import com.hhplus.backend.domain.queue.QueueRepository;
import com.hhplus.backend.domain.queue.QueueService;
import com.hhplus.backend.domain.queue.UserToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DisplayName("사용자 토큰 조회 및 상태변경 테스트")
public class QueueServiceTest {

    @InjectMocks
    private QueueService queueService;

    @Mock
    private QueueRepository queueRepository;
    private UserToken basicToken;
    private List<UserToken> basicAccessToken;
    private List<UserToken> basicWaitToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // 기본 객체 세팅
        long id = 1;
        long userId = 1;
        String accessToken = UUID.randomUUID().toString();
        basicToken = new UserToken(id, userId, accessToken, "WAIT" , LocalDateTime.now(), LocalDateTime.now());
        basicAccessToken = new ArrayList<>();
        for (int i=0; i<25; i++) {
            this.basicAccessToken.add(new UserToken(id, userId, accessToken, "ACTIVE" , LocalDateTime.now(), LocalDateTime.now().minusMinutes(i)));
        }
        basicWaitToken = new ArrayList<>();
        for (int i=0; i<10; i++) {
            this.basicWaitToken.add(new UserToken(id, userId, accessToken, "WAIT" , LocalDateTime.now(), LocalDateTime.now()));
        }
    }

    @Test
    @DisplayName("사용자 토큰 조회 테스트")
    public void getTokenTest() {
        // given
        long userId = 1;
        given(queueRepository.findToken(anyLong())).willReturn(basicToken);

        // when
        UserToken result = queueService.getToken(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getAccessToken()).isEqualTo(basicToken.getAccessToken());
    }

    @Test
    @DisplayName("토큰 발급 테스트 - 토큰이 원래 있었어도 요청하면 만료시키고 다시 발급")
    public void issueTokenTest() {
        // given
        long id = 1;
        long userId = 1;
        String accessToken = UUID.randomUUID().toString();
        String status = "WAIT";
        UserToken alreadyToken = new UserToken(id, userId, accessToken, status , LocalDateTime.now().minusMinutes(3), LocalDateTime.now().minusMinutes(1));
        given(queueRepository.findToken(anyLong())).willReturn(alreadyToken);
        alreadyToken.expire();
        //given(queueRepository.saveUserToken(any())).willReturn(alreadyToken);
        given(queueRepository.saveUserToken(any())).willReturn(null);
        given(queueRepository.saveUserToken(any())).willReturn(basicToken);

        // when
        UserToken result = queueService.issueToken(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getAccessToken()).isEqualTo(basicToken.getAccessToken());
        assertThat(result.getStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("토큰 상태 활성화 테스트")
    public void activeTokensTest() {
        // given
        long activeTokenCount = 25;
        given(queueRepository.getActiveTokenCount()).willReturn(activeTokenCount);
        long activeCount = Queue.availableActiveSeats(activeTokenCount);
        given(queueRepository.getOldestWatingTokens(anyLong())).willReturn(basicWaitToken);
        given(queueRepository.saveAll(any())).willReturn(1);

        // then
        queueService.activateTokens();
    }

    @Test
    @DisplayName("토큰 상태 만료(단건) 테스트")
    public void expireTokenTest() {
        // given
        long userId = 1;
        given(queueRepository.findToken(anyLong())).willReturn(basicToken);
        basicToken.expire();
        given(queueRepository.saveUserToken(any())).willReturn(basicToken);

        // when
        UserToken result = queueService.expireToken(userId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getAccessToken()).isEqualTo(basicToken.getAccessToken());
        assertThat(result.getStatus()).isEqualTo("EXPIRED");
    }

    @Test
    @DisplayName("토큰 상태 만료(여러건) 테스트")
    public void expireTokensTest() {
        // given
        given(queueRepository.getOver5MinActiveTokens()).willReturn(basicAccessToken);

        // then
        queueService.expireTokens();
    }
}
