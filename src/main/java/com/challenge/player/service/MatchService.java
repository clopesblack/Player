package com.challenge.player.service;

import com.challenge.player.exception.OpponentNotAvailableToPlayException;
import com.challenge.player.model.Match;
import com.challenge.player.model.Player;
import com.challenge.player.service.client.OpponentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final GameHelper gameHelper;
    private final OpponentClient opponentClient;

    @Async("gameplayExecutor")
    public void playNewMatch(final Match match) {
        final Match result = gameHelper.gameLoop(match);
        log.info("End game, winner={}", result.getWinner().getId());
    }

    public void checkAvailability(final Player opponent) {
        log.info("opponentAddress={}, msg={}", opponent.getAddress(), "Checking availability");
        if (!opponentClient.isAvailable(opponent)) {
            throw new OpponentNotAvailableToPlayException();
        }
    }
}
