package com.hhplus.backend.controller.payment;

import com.hhplus.backend.application.payment.PaymentTokenFacade;
import com.hhplus.backend.controller.payment.dto.PaymentDto;
import com.hhplus.backend.domain.payment.PaymentCommand;
import com.hhplus.backend.support.event.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    KafkaController(KafkaProducer producer){
        this.producer = producer;
    }

    @PostMapping
    @ResponseBody
    public String sendMessage(@RequestBody String message) {
        log.info("message : {}", message);
        this.producer.create(message);

        return "success";
    }
}
