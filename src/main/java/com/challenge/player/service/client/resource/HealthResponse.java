package com.challenge.player.service.client.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;

@Builder
@JsonDeserialize(builder = HealthResponse.HealthResponseBuilder.class)
public class HealthResponse {

    private static final String UP_STATUS = "UP";

    private final String status;

    public boolean isUp() {
        return UP_STATUS.equals(this.status);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class HealthResponseBuilder {

    }
}
