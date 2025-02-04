package com.accessqueuesystem.accessqueuesystem.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/loadtest")
@RequiredArgsConstructor
public class LoadTestController {

    private static final String TEST_SERVER_URL = "http://13.209.190.211";

    @PostMapping("/start")
    public ResponseEntity<String> startLoadTest() {
        try {
            // 테스트 서버로 요청 전송
            String testServerUrl = TEST_SERVER_URL + "/api/v1/k6/start"; // 테스트 서버 URL

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.postForEntity(testServerUrl, null, String.class);

            return ResponseEntity.ok("k6 부하 테스트 요청 성공: " + response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("k6 요청 실패: " + e.getMessage());
        }
    }
}
