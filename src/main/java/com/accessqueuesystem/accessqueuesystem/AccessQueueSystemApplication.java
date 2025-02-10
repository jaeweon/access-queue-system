package com.accessqueuesystem.accessqueuesystem;

import com.accessqueuesystem.accessqueuesystem.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Controller
@RequiredArgsConstructor
@Slf4j
public class AccessQueueSystemApplication {

    private final RestTemplateUtil restTemplateUtil;

    // AWS 도메인 설정
    private static final String QUEUE_SERVER_URL = "http://waitingroom.reservate.click";
    private static final String LOGIN_SERVER_URL = "http://reservate.click";

    // 최대 500개 요청만 동시 허용, 추가 요청은 대기 (대기 큐 크기: 500)
    private static final Semaphore SEMAPHORE = new Semaphore(500, true);

    public static void main(String[] args) {
        SpringApplication.run(AccessQueueSystemApplication.class, args);
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "queue", defaultValue = "default") String queue,
                        @RequestParam(name = "user_id") String userId) {

        // 최대 대기 시간 15초 설정 (기다려도 응답을 못 받으면 실패 처리)
        try {
            if (!SEMAPHORE.tryAcquire(15, TimeUnit.SECONDS)) {
                log.warn("Too many requests - redirecting to error: userId={}", userId);
                return "redirect:/error"; // 서버 과부하
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "redirect:/error";
        }

        try {
            // Queue 서버 API 호출 URL
            String queueApiUrl = QUEUE_SERVER_URL + "/api/v1/queue/allowed";

            AllowedUserResponse allowedResponse = null;
            try {
                allowedResponse = restTemplateUtil.callQueueApi(queueApiUrl, queue, userId);
            } catch (Exception e) {
                log.error("Queue API request failed", e);
                return "redirect:/error";
            }

            // Queue 서버에서 허용되지 않으면 대기실로 리다이렉트
            if (allowedResponse == null || !allowedResponse.allowed()) {
                String redirectUrl = "%s/waiting-room?user_id=%s&redirect_url=%s".formatted(
                        QUEUE_SERVER_URL, userId, LOGIN_SERVER_URL + "?user_id=" + userId
                );
                return "redirect:" + redirectUrl;
            }

            // 정상적으로 허용되면 메인 페이지로 이동
            return "redirect:/course";

        } finally {
            // 세마포어 해제
            SEMAPHORE.release();
        }
    }

    public record AllowedUserResponse(Boolean allowed) {}
}
