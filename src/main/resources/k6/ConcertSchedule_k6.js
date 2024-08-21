import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    // vus: 1000,             /* 병렬로 while(true)를 얼마나 돌릴것인지 */
    // duration: '5s',     /* 이 테스트를 얼마나 돌릴 것인지 */
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

    check(res, {    /* 테스트 통과 조건 */
        'status is 200': (r) => r.status === 200,
        'response time is less than 500ms': (r) => r.timings.duration < 500,
    });
    sleep(1);
}