package com.challenge.player.service;

import com.challenge.player.exception.OpponentNotAvailableException;
import com.challenge.player.model.Match;
import com.challenge.player.model.Player;
import com.challenge.player.service.client.MatchClient;
import com.challenge.player.service.client.OpponentClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchClient client;
    private final OpponentClient opponentClient;

    @Async("gameplayExecutor")
    public void playNewMatch(final Match match) {
        final Match lastMatch = playNextMatch(match);
        log.info("End game, winner={}", lastMatch.getWinner().getId());
    }

    public void checkAvailability(final Player opponent) {
        log.info("opponentAddress={}, msg={}", opponent.getAddress(), "Checking availability");
        if (!opponentClient.isAvailable(opponent)) {
            throw new OpponentNotAvailableException();
        }
    }

    private Match playNextMatch(final Match match) {
        if (match.isFinish()) {
            return match;
        }
        return playNextMatch(client.playNewMatch(match).get());
    }
}
