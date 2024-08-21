import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 100,             /* 병렬로 while(true)를 얼마나 돌릴것인지 */
    iterations: 1,     /* 각 사용자가 한번의 요청만 보냄 */
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