# Chapter4. 장애대응 : K6로 부하테스트 하기

## 콘서트 좌석 예약 서비스
주요 API 
1. 콘서트 스케쥴 조회
2. 콘서트 좌석 조회
3. 좌석 예약
4. 결제
5. 포인트 충전

### 1. 콘서트 스케쥴&좌석 조회
#### 테스트 시나리오
- 콘서트 예매가 시작되었을 때 특정 시간대에 트래픽 급증
- 사용자들이 자주 조회하는 인기 좌석과 인기 없는 좌석에 대한 조회 패턴 설정
- 한 사용자가 여러 번 좌석 조회하는 연속적인 조회 테스트

```
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    scenarios: {
        steady_load: {
            executor: 'constant-arrival-rate',
            rate: 50, // 초당 50개의 요청을 발생
            timeUnit: '1s', // rate가 적용될 단위 시간
            duration: '5s', // 5초 동안 지속
            preAllocatedVUs: 20, // 미리 할당된 VU의 수
            maxVUs: 100, // 필요시 사용될 최대 VU의 수
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.01'], /* http error가 1% 이하 */
        http_req_duration: ['p(95)<200'], /* 95%의 요청이 200ms 아래 */
    }
};

export default function () {
    let res = http.get('http://192.168.0.139:8080/concert/1/schedule');
    // or
    let res = http.get('http://192.168.0.139:8080/concert/1/seat');   

    check(res, {    /* 테스트 통과 조건 */
        'status is 200': (r) => r.status === 200,
        'response time is less than 500ms': (r) => r.timings.duration < 500,
    });
    sleep(1);
}
```

#### 테스트 결과
![image](https://github.com/user-attachments/assets/a9ab742f-9a32-4319-8174-b4429b549af3)


### 2. 좌석 예약 요청
#### 테스트 시나리오
- 여러 사용자가 동일한 좌석에 대해 동시에 예약 요청 (동시성 테스트)
- 하나의 좌석에 대해 여러 요청이 보내졌을 때, 얼마나 많은 요청이 성공하고 실패하는지를 측정

```
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 50,            
    iterations: 50,  
    thresholds: {
        http_req_failed: ['rate<0.1'], /* http error가 1% 이하 */
        http_req_duration: ['p(95)<200'], /* 95%의 요청이 200ms 아래 */
    }
};

export default function () {
     let url = 'http://192.168.0.139:8080/concert/reservation';
     let payload = JSON.stringify({
        "userId":1,
        "concertId":1,
        "scheduleId": 1,
        "seatId":6
     });

     let params = {
        headers: {
            'Content-Type': 'application/json',
        },
     }
    
     let res = http.post(url, payload, params);

    check(res, {    /* 테스트 통과 조건 */
        'status is 200': (r) => r.status === 200,
        'seat not double-booked': (r) => r.json().message !== '이미 예약된 좌석입니다.',
    });
    sleep(1);
}
```

#### 테스트 결과
![image](https://github.com/user-attachments/assets/5c49141d-6ea2-4562-8a65-4a294e56d53f)
-> 뭔가 이상하다... LOCK 다시 봐야할듯...

### 3. 결제 요청
#### 테스트 시나리오
- 여러 사용자가 동시에 결제 요청 (동시성 테스트)
- 결제가 완료된 예약좌석을 결제 요청할 경우 검증 후 얼마나 많은 요청이 성공하고 실패하는지를 측정
  - 유효한 예약 좌석인가?
  - 포인트가 충분하가?

```
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,             /* 병렬로 while(true)를 얼마나 돌릴것인지 */
    iterations: 10,     /* 각 사용자가 한번의 요청만 보냄 */
    thresholds: {
        http_req_failed: ['rate<0.1'], /* http error가 1% 이하 */
        http_req_duration: ['p(95)<200'], /* 95%의 요청이 200ms 아래 */
    }
};

export default function () {
     let url = 'http://192.168.0.139:8080/payment/pay';
     let payload = JSON.stringify({
        "userId":1,
        "usePoint": 3000,
        "seatReservation": {
            "id": 1,
            "concertId": 1,
            "userId": 1,
            "scheduleId": 1,
            "seatId": 5
        }
     });

     let params = {
        headers: {
            'Content-Type': 'application/json',
        },
     }
    
     let res = http.post(url, payload, params);

    check(res, {    /* 테스트 통과 조건 */
        'status is 200': (r) => r.status === 200,
        'already payment seat': (r) => r.json().message !== '이미 결제가 완료된 좌석입니다.',
    });
    sleep(1);
}
```
> cannot deserialize from Object value 오류가 발생하여 확인이 필요함

### 4. 포인트 충전
#### 테스트 시나리오
- 사용자가 동시에 여러번 포인트 충전 (동시성 테스트)
- 최종 포인트가 정확하게 계산 되었는가 확인 (일관성, 무결성)

```
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,             /* 병렬로 while(true)를 얼마나 돌릴것인지 */
    duration: '5s',  // 1분 동안 테스트 실행
    thresholds: {
        http_req_failed: ['rate<0.1'], /* http error가 1% 이하 */
        http_req_duration: ['p(95)<200'], /* 95%의 요청이 200ms 아래 */
    }
};

export default function () {
    const userId = 1;
    let url = 'http://192.168.0.139:8080/user/point/'+userId+'/charge';
    let payload = JSON.stringify(1000);;
    let params = {
        headers: {
            'Content-Type': 'application/json',
        },
    }

    let res = http.patch(url, payload, params);

    check(res, {    /* 테스트 통과 조건 */
        'status is 200': (r) => r.status === 200,
        'correct points added': (r) => r.json().new_balance === 100,
    });
    sleep(1);
}
```

#### 테스트 결과
![image](https://github.com/user-attachments/assets/6f1356ce-1e98-4288-9d45-404099dfcfdd)
