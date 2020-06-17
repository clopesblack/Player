package com.challenge.player.model;

import org.junit.jupiter.api.Test;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    @Test
    public void buildMatch_ShouldGenerateIdIfNotExists() {
        final Match match = Match.builder()
                .move(getMove(56, getPlayer()))
                .challenger(getPlayer())
                .opponent(getPlayer())
                .build();
        assertNotNull(match.getId());
    }

    @Test
    public void buildMatch_ShouldNotChangeIdIfExists() {
        final String idGenerated = randomUUID().toString();
        final Match match = Match.builder()
                .id(idGenerated)
                .move(getMove(30, getPlayer()))
                .challenger(getPlayer())
                .opponent(getPlayer())
                .build();
        assertEquals(idGenerated, match.getId());
    }

    @Test
    public void buildMatch_ShouldGameEndedWhenSetWinnerWhenMoveValueIsEqualToOne() {
        final Player winner = getPlayer();
        final Match match = Match.builder()
                .move(getMove(1, winner))
                .challenger(getPlayer())
                .opponent(winner)
                .build();
        assertTrue(match.isGameEnded());
        assertEquals(winner.getId(), match.getWinner().getId());
    }

    private Player getPlayer() {
        return Player.builder().address("localhost:8082").build();
    }

    private Move getMove(final Integer value, final Player owner) {
        return Move.builder().value(value).owner(owner).build();
    }
}
