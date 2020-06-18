package com.challenge.player.service;

import com.challenge.player.model.Match;
import com.challenge.player.model.Move;
import com.challenge.player.model.Player;
import com.challenge.player.service.client.OpponentClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameHelperTest {

    private Player selfPlayer;
    private GameHelper gameHelper;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        selfPlayer = Player.builder().address("localhost:8081").build();
        gameHelper = new GameHelper(selfPlayer, new OpponentClient(restTemplate));
    }

    @Test
    public void gameLoop_WithOpponentAvailable_ShouldNotThrowsOpponentNextMoveRequestException() {
        final Match match = buildMatch(getMove(90, selfPlayer));

        when(restTemplate.postForObject(anyString(), any(), eq(Match.class)))
                .thenReturn(buildMatch(getMove(match.getMove().getValue(), match.getOpponent()).nextMove()));

        final Match resultMatch = gameHelper.gameLoop(match);
        Assertions.assertEquals(1, resultMatch.getMove().getValue());
    }

    private Match buildMatch(final Move move) {
        return Match.builder().challenger(selfPlayer).opponent(getOpponent()).move(move).build();
    }

    private Move getMove(final Integer value, final Player owner) {
        return Move.builder().owner(owner).value(value).build();
    }

    private Player getOpponent() {
        return Player.builder().address("http://localhost:8082").build();
    }
}
