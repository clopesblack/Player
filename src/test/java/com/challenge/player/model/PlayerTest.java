package com.challenge.player.model;

import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerTest {

    @Test
    public void playerBuilder_ShouldGenerateIdIfNotPassed() {
        final String address = "localhost:8080";
        final Player player = Player.builder().address(address).build();
        assertNotNull(player.getId());
        assertEquals(address, player.getAddress());
    }

    @Test
    public void playerBuilder_ShouldNotGenerateIdIfIsPassed() {
        final String idGenerated = randomUUID().toString();
        final Player player = Player.builder().id(idGenerated).build();
        assertEquals(idGenerated, player.getId());
    }
}
