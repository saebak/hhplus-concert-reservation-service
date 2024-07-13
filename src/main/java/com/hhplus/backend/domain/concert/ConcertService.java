package com.hhplus.backend.domain.concert;

import com.hhplus.backend.controller.concert.dto.ConcertInput;
import com.hhplus.backend.controller.concert.dto.ReserveSeatInput;
import java.time.LocalDateTime;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    public ConcertService (ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    // 콘서트 예약 가능 날짜 조회
    public List<ConcertSchedule> getConcertSchedules(ConcertCommand.GetConcertSchedules command) {
        List<ConcertSchedule> schedules = concertRepository.getConcertSchedules(command.concertId);
        return schedules;
    }
    
    // 콘서트 좌석 조회
    public List<ConcertSeat> getConcertSeats(ConcertCommand.GetConcertSeats command) {
        List<ConcertSeat> seats = concertRepository.getConcertSeats(input);
        return seats;
    }

    // 좌석 예약 요청
    public SeatReservation reserveSeat(ConcertCommand.GetSeatReservation command) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        // 그다음 그 콘서트의 스케쥴 봐 (날짜가 지났는지 등등)
        ConcertSchedule concertSchedule = concertRepository.findConcertScheduleById(conertId, scheduleId);
        concertSchedule.checkReservationCondition();

        ConcertSeat seat = concertRepository.findConcertSeat(concertId, scheduleId, seatId);
        if(seat == null) throw RuntimeException("없는 좌석입니다.");


        // 다른 사람예약이 점유중인지 확인해야함.
        SeatReservation seatReservation = concertRepository.findValidSeatReservation(concertId, scheduleId, seatId, now);
        seatReservation.checkReserved(now);

        SeatReservation newReservation = new SeatReservation(userId, Concert, Schedule , Seat);
        return concertRepository.saveSeatReservation(newReservation);
    }
}

fun order(LIst<product> products) {
    List<OrderItem> orderItems = products.stream().map(product -> new OrderItem(product));
    Order order = new Order(orderItems);
    orderRepoistory.saveOrder(order);
}

class Order {
    List<OrderItem> orderItems;

}
class orderREpoistoryImpl {
    saveOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity(order);
        List<OrderItemEntity> orderItemEntites = order.orderItems.stream().map(orderITem -> new OrderItemEntity(orderItem));

        OrderJpaRespositoy.save(orderEntity);
        OrderItemJpaRespitory.save(orderItemEnties);
    }
}
OrderJpaRespositoy.
OrderItemJpaRespitory.