package com.accessqueuesystem.accessqueuesystem.util;

import com.accessqueuesystem.accessqueuesystem.AccessQueueSystemApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class RestTemplateUtil {
    private final RestTemplate restTemplate = new RestTemplate();

    public AccessQueueSystemApplication.AllowedUserResponse callQueueApi(String baseUrl, String queue, String userId) {
        URI uri = buildQueueUri(baseUrl, queue, userId);
        ResponseEntity<AccessQueueSystemApplication.AllowedUserResponse> response = restTemplate.getForEntity(uri, AccessQueueSystemApplication.AllowedUserResponse.class);
        return response.getBody();
    }

    public URI buildQueueUri(String baseUrl, String queue, String userId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);

        if (!baseUrl.contains(":")) { // 포트가 이미 있다면 추가하지 않음
            builder.port(9010);
        }

        return builder
                .queryParam("queue", queue)
                .queryParam("user_id", userId)
                .encode()
                .build()
                .toUri();
    }
}
