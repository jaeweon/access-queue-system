package com.accessqueuesystem.accessqueuesystem;

import com.accessqueuesystem.accessqueuesystem.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
@RequiredArgsConstructor
public class AccessQueueSystemApplication {

    private final RestTemplateUtil restTemplateUtil;

    public static void main(String[] args) {
        SpringApplication.run(AccessQueueSystemApplication.class, args);
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "queue", defaultValue = "default") String queue,
                        @RequestParam(name = "user_id") String userId) {

        String redirectUrl = "http://127.0.0.1:9000?user_id=%s".formatted(userId);
        String waitingRoomUrl = "http://127.0.0.1:9010/waiting-room?user_id=%s&redirect_url=%s".formatted(userId, redirectUrl);

        System.out.println("Redirecting to: " + waitingRoomUrl); // 로그 추가

        // Allowed API 호출
        AllowedUserResponse allowedResponse = restTemplateUtil.callQueueApi(
                "http://127.0.0.1:9010/api/v1/queue/allowed",
                queue,
                userId
        );

        if (allowedResponse == null || !allowedResponse.allowed()) {
            // 대기 페이지로 리다이렉트
            return "redirect:http://127.0.0.1:9010/waiting-room?user_id=%s&redirect_url=%s".formatted(
                    userId, "http://127.0.0.1:9000?user_id=%s".formatted(userId));
        }

        // 메인 페이지로 이동
        return "redirect:/course";
    }

    public record AllowedUserResponse(Boolean allowed) {}
}