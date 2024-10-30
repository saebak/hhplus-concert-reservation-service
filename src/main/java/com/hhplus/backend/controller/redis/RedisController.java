package com.hhplus.backend.controller.redis;

import com.hhplus.backend.domain.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private QueueService queueService;

    @GetMapping("/token/issue")
    public ResponseEntity<?> setKeyValue() {
        return ResponseEntity.ok().build();
    }

}
