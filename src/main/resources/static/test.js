import http from 'k6/http';
import { check } from 'k6';

export let options = {
    vus: 1000, // 동시 사용자 1000명
    duration: '30s', // 30초 동안 실행
};

export default function () {
    let params = {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    };

    let payload = `username=dummy_user_${__VU}&password=defaultPassword123`;

    let res = http.post('http://127.0.0.1:9000/api/v1/auth/login', payload, params);

    check(res, {
        '로그인 요청 성공': (r) => r.status === 200 || r.status === 302,
    });
}
