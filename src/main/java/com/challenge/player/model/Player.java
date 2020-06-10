package com.challenge.player.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import static java.util.UUID.randomUUID;

@Getter
@JsonDeserialize(builder = Player.PlayerBuilder.class)
public class Player {

    private final String id;
    private final String address;

    @Builder(builderClassName = "PlayerBuilder", toBuilder = true)
    private Player(final String address, final String id) {
        this.address = address;
        this.id = createIdIfNotExists(id);
    }

    private String createIdIfNotExists(final String id) {
        if (null == id) {
            return randomUUID().toString();
        }
        return id;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class PlayerBuilder {
    }
}

