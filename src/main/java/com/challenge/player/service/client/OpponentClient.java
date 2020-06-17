package com.challenge.player.service.client;

import com.challenge.player.model.Player;
import com.challenge.player.service.client.resource.HealthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpponentClient {

    private static final String ACTUATOR_HEALTH = "/actuator/health";
    private final RestTemplate restTemplate;

    public boolean isAvailable(final Player opponent) {
        try {
            final ResponseEntity<HealthResponse> response = restTemplate.getForEntity(getHealthUrl(opponent), HealthResponse.class);
            return isUp(response);
        } catch (RuntimeException e) {
            log.info("opponentAddress={}, msg={}, e={}", opponent.getAddress(), "Error on try consume opponent", e.getMessage());
            return false;
        }
    }

    private String getHealthUrl(final Player opponent) {
        return opponent.getAddress() + ACTUATOR_HEALTH;
    }

    private boolean isUp(final ResponseEntity<HealthResponse> response) {
        return response.getStatusCode().is2xxSuccessful() && null != response.getBody() && response.getBody().isUp();
    }
}
