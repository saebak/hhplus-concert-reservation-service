package com.hhplus.backend.kafka;

import com.hhplus.backend.domain.payment.*;
import com.hhplus.backend.domain.user.UserService;
import com.hhplus.backend.support.event.PaymentSuccessEvent;
import com.hhplus.backend.support.event.kafka.KafkaConsumer;
import com.hhplus.backend.support.event.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@EmbeddedKafka(partitions = 3, topics = { "paymentEvent" }, brokerProperties = { "listeners=PLAINTEXT://localhost:9092"}, ports= { 9092 })
@DisplayName("결제 로직 kafka + outbox 패턴 변경 테스트")
public class PaymentServiceKafkaOutboxPatternTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private PaymentRepository paymentRepository;


    @BeforeEach
    void before() throws Exception {
        userService.chargePoint(1, 100000);
        log.info("사용자 포인트 충전 성공 : {} ", userService.getUserPoint(1).toString());
    }

    @Test
    @DisplayName("카프카 메세지 발행 및 소비 테스트")
    public void paymentKafkaProducerTest() throws Exception {
        // given
        LocalDateTime date = LocalDateTime.now();
        PaymentSuccessEvent paymentSuccessEvent = new PaymentSuccessEvent(2L, 1L, 20000, "payment", date);
        kafkaProducer.publishPaymentInfo(paymentSuccessEvent);
    }

    @Test
    @DisplayName("Outbox 테이블 데이터 저장 테스트")
    public void paymentKafkaOutboxPatternTest() throws Exception {
        // given
        PaymentCommand.GetConcertSeatReservation command = new PaymentCommand.GetConcertSeatReservation();
        command.concertId = 1L;
        command.scheduleId = 1L;
        command.seatId = 5L;
        command.userId = 1L;
        command.price = 30000;

        // when
        paymentService.payKafkaOutboxPattern(command);      // kafka message 발행 및 outbox 저장 테스트
        List<PaymentOutbox> paymentOutboxList = paymentService.getPaymentOutboxs(); // outbox 저장 확인

        // then
        System.out.println("!!!!!!!!!!outbox 저장 테스트 : " + paymentOutboxList.toString());
        assertThat(paymentOutboxList.size()).isGreaterThan(0);
        assertThat(paymentOutboxList.get(0).getStatus()).isEqualTo("PUBLISHED");
    }

    @Test
    @DisplayName("outbox 발행 실패한 messege 재시도 스케쥴러 테스트")
    public void paymentOutboxInitSchedulerTest() throws Exception {
        // given
        PaymentSuccessEvent successEvent = new PaymentSuccessEvent(1L, 1L, 20000, "PAYMENT", LocalDateTime.now());
        PaymentOutbox outbox = new PaymentOutbox(1L, "paymentEvent", "RECEIVED", successEvent);
        paymentRepository.savePaymentOutbox(outbox);

        // when
        List<PaymentOutbox> paymentOutboxs = paymentService.getPaymentOutboxsByStatus("paymentEvent", "INIT");    // 쿼리로 시간 비교 , 실무에선 페이지네이션 필요(데이터 많으니깐)
        for (PaymentOutbox paymentOutbox : paymentOutboxs) {
//            PaymentPayload payload = new PaymentPayload(paymentOutbox.getPayload());
            kafkaProducer.publishPaymentInfo(paymentOutbox.getPayload());
        }

        List<PaymentOutbox> paymentInitOutboxs = paymentService.getPaymentOutboxsByStatus("paymentEvent", "INIT");

        // then
        System.out.println("!!!!!!!!!!outbox 저장 테스트 : " + paymentInitOutboxs.toString());
        assertThat(paymentInitOutboxs.size()).isEqualTo(0);

    }

}
