package com.hhplus.backend.controller.token;

import com.hhplus.backend.application.concert.ConcertMockFacade;
import com.hhplus.backend.domain.token.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final ConcertMockFacade concertMockFacade;

    /**
     * 사용자 대기열 토큰 발급 요청
     * @param input
     * @return
     */
    @PostMapping("/create")
    public UserToken createToken(@RequestBody Map<String, Long> input) {
        UserToken userToken = concertMockFacade.getTokenMock();
        return userToken;
    }

    /**
     * 사용자 대기열 토큰 만료
     * @param input
     * @return
     */
    @PostMapping("/expired")
    public UserToken expiredToken(@RequestBody Map<String, Long> input) {
        UserToken userToken = concertMockFacade.getTokenMock();
        return userToken;
    }
}
