package com.challenge.player.service;

import com.challenge.player.model.Match;
import com.challenge.player.model.Player;
import com.challenge.player.service.client.MatchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameHelper {

    private final Player selfPlayer;
    private final MatchClient client;

    public Match gameLoop(final Match match) {
        if (match.isGameEnded()) {
            return match;
        }
        return gameLoop(nextMove(match));
    }

    private Match nextMove(final Match match) {
        if (isMyTurn(match)) {
            return playMyMove(match);
        }
        return askOpponentNextMove(match);
    }

    private boolean isMyTurn(Match match) {
        return !match.getMove().getOwner().equals(selfPlayer);
    }

    private Match playMyMove(final Match match) {
        return match.builderForTheSameMatch()
                .move(match.getMove().nextMove())
                .build();
    }

    private Match askOpponentNextMove(final Match match) {
        return client.playNewMatch(match);
    }
}
