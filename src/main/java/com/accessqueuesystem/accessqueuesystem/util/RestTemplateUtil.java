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

    public AccessQueueSystemApplication.AllowedUserResponse callQueueApi(String baseUrl, String queue, Long userId) {
        URI uri = buildQueueUri(baseUrl, queue, userId);
        ResponseEntity<AccessQueueSystemApplication.AllowedUserResponse> response = restTemplate.getForEntity(uri, AccessQueueSystemApplication.AllowedUserResponse.class);
        return response.getBody();
    }

    public URI buildQueueUri(String baseUrl, String queue, Long userId) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("queue", queue)
                .queryParam("user_id", userId)
                .encode()
                .build()
                .toUri();
    }
}
