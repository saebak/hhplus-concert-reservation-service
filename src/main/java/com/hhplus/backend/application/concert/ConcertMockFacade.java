package com.hhplus.backend.application.concert;


import com.hhplus.backend.domain.concert.Concert;
import com.hhplus.backend.domain.concert.ConcertSchedule;
import com.hhplus.backend.domain.concert.ConcertSeat;
import com.hhplus.backend.domain.concert.UserToken;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class ConcertMockFacade {

    private List<Concert> concerts = concertsMock();
    private List<ConcertSchedule> schedules = schedulesMock(1);
    private List<ConcertSeat> seats = seatsMock(2);
    private UserToken tokenMock = getTokenMock(1);

    // 콘서트 더미 데이터
    private List<Concert> concertsMock() {
        List<Concert> concertList = new ArrayList<>();
        concertList.add(new Concert(1L, "뮤지컬 데스노트", "원작 데스노트를 뮤지컬로 만들었어용", 70000));
        concertList.add(new Concert(2L, "한요한 정규3집 단독 콘서트","한요한 콘서트 가고싶다", 50000));

        return concertList;
    }

    // 콘서트스케쥴 더미 데이터
    private List<ConcertSchedule> schedulesMock(long concertId) {
        List<ConcertSchedule> schedules = new ArrayList<>();
        schedules.add(new ConcertSchedule(1L, concertId, LocalDateTime.now()));
        schedules.add(new ConcertSchedule(2L, concertId, LocalDateTime.now().plusDays(7)));

        return schedules;
    }

    // 콘서트 좌석 더미 데이터
    private List<ConcertSeat> seatsMock(long scheduleId) {
        List<ConcertSeat> seats = new ArrayList<>();
        for (int i=0; i<10; i++) {
            seats.add(new ConcertSeat((long) i, scheduleId, i));
        }

        return seats;
    }

    // 유저 대기열 토큰 더미 데이터
    private UserToken getTokenMock(long userId) {
        UserToken userToken = new UserToken(1L, userId, "accessToken1111111222222", "WAIT", LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));

        return userToken;
    }
}
