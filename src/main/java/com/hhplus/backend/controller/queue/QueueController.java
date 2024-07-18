package com.hhplus.backend.controller.queue;

import com.hhplus.backend.domain.queue.QueueService;
import com.hhplus.backend.domain.queue.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class QueueController {

    @Autowired
    private QueueService queueService;

    /**
     * 사용자 대기열 토큰 발급 요청
     * @param userId
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<UserToken> createToken(@RequestBody Long userId) {
        UserToken userToken = queueService.issueToken(userId);
        return ResponseEntity.ok().body(userToken);
    }

    /**
     * 사용자 대기열 토큰 만료 (직접 api를 호출할 일이 없을 것 같은데 굳이 필요한가..?)
     * @param input
     * @return
     */
//    @PostMapping("/expired")
//    public ResponseEntity<UserToken> expiredToken(@RequestBody Map<String, Long> input) {
//        UserToken userToken = queueService.expireTokens();
//        return userToken;
//    }
}
