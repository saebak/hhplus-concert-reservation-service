import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 100,             /* 병렬로 while(true)를 얼마나 돌릴것인지 */
    duration: '5s',  // 1분 동안 테스트 실행
    thresholds: {
        http_req_failed: ['rate<0.1'], /* http error가 1% 이하 */
        http_req_duration: ['p(95)<200'], /* 95%의 요청이 200ms 아래 */
    }
};

export default function () {
     const userId = 1;
     let url = 'http://192.168.0.139:8080/user/point/'+userId+'/charge';
     let payload = 1000;
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