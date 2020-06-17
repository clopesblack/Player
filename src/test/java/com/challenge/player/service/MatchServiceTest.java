package com.challenge.player.service;

import com.challenge.player.exception.OpponentNextMoveRequestException;
import com.challenge.player.exception.OpponentNotAvailableToPlayException;
import com.challenge.player.model.Match;
import com.challenge.player.model.Move;
import com.challenge.player.model.Player;
import com.challenge.player.service.client.MatchClient;
import com.challenge.player.service.client.OpponentClient;
import com.challenge.player.service.client.resource.HealthResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    private Player selfPlayer;
    private MatchService service;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        MatchClient client = new MatchClient(restTemplate);
        selfPlayer = Player.builder().address("localhost:8081").build();
        service = new MatchService(new GameHelper(selfPlayer, client), new OpponentClient(restTemplate));
    }

    @Test
    public void playNewMatch_WithOpponentConnectionIssues_ShouldThrowsOpponentNextMoveRequestException() {
        final Match match = Match.builder()
                .challenger(selfPlayer)
                .opponent(getOpponent())
                .move(Move.builder().value(90).owner(selfPlayer).build())
                .build();

        when(restTemplate.postForObject(anyString(), any(), eq(Match.class)))
                .thenThrow(OpponentNextMoveRequestException.class);

        Assertions.assertThrows(OpponentNextMoveRequestException.class,
                () -> service.playNewMatch(match));
    }

    @Test
    public void checkAvailability_WithOpponentAvailable_ShouldNotThrowOpponentNotAvailableToPlayException() {
        when(restTemplate.getForEntity(anyString(), eq(HealthResponse.class)))
                .thenReturn(ResponseEntity.ok(HealthResponse.builder()
                        .status("UP")
                        .build()));

        service.checkAvailability(getOpponent());
    }

    @Test
    public void checkAvailability_WithOpponentNotAvailable_ShouldThrowOpponentNotAvailableToPlayException() {
        when(restTemplate.getForEntity(anyString(), eq(HealthResponse.class)))
                .thenThrow(OpponentNotAvailableToPlayException.class);

        Assertions.assertThrows(OpponentNotAvailableToPlayException.class,
                () -> service.checkAvailability(getOpponent()));
    }

    private Player getOpponent() {
        return Player.builder().address("http://localhost:8082").build();
    }
}
