package com.challenge.player.controller.resource;

import com.challenge.player.model.Player;
import lombok.Data;

@Data
public class StartMatchRequest {

    private Player opponent;
    private Integer value;

    public StartMatchRequest(Player opponent, Integer value) {
        this.opponent = opponent;
        this.value = value;
    }
}
