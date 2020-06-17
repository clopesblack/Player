package com.challenge.player.exception;

import com.challenge.player.model.Match;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpponentNextMoveRequestException extends RuntimeException {

    public OpponentNextMoveRequestException(final Match match) {
        super();
        log.info("matchId={}, lastMove={}, msg={}",
                match.getId(), match.getMove().getValue(),
                "The match was unable to continue due to failing to get the next move from the opponent.");
    }
}
