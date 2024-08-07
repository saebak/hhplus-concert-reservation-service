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
    public ResponseEntity<Long> createToken(@RequestBody Long userId) throws Exception {
        //UserToken userToken = queueService.issueToken(userId);
        long waitingNo = queueService.issueTokenRedis(userId);
        return ResponseEntity.ok().body(waitingNo);
    }

}
