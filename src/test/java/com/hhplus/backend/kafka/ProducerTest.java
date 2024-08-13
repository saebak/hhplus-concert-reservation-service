package com.hhplus.backend.kafka;

import com.hhplus.backend.support.event.kafka.ProducerCreate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProducerTest {
    @Autowired
    private ProducerCreate producerCreate;

    @Test
    void setTestProducer() {
        producerCreate.create();
    }

}
