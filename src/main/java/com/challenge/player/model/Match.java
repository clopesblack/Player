package com.challenge.player.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.MDC;

import static java.util.UUID.randomUUID;

@Getter
@JsonDeserialize(builder = Match.MatchBuilder.class)
public class Match {

    @NonNull
    private final String id;

    @NonNull
    private final Move move;

    @NonNull
    private final Player challenger;

    @NonNull
    private final Player opponent;

    private final boolean gameEnded;
    private final Player winner;

    @Builder(builderClassName = "MatchBuilder", toBuilder = true)
    private Match(final Move move, final String id, final Player opponent, final Player challenger, final Player winner) {
        this.move = move;
        this.id = createIdIfNotExists(id);
        this.opponent = opponent;
        this.challenger = challenger;
        this.gameEnded = move.isEndGameMove();
        this.winner = initializeWinner(move, winner);
        MDC.put("matchId", this.id);
    }

    public MatchBuilder builderForTheSameMatch() {
        return Match.builder()
                .id(this.id)
                .challenger(this.challenger)
                .opponent(this.opponent)
                .winner(this.winner);
    }

    private String createIdIfNotExists(final String id) {
        if (null == id) {
            return randomUUID().toString();
        }
        return id;
    }

    private Player initializeWinner(final Move move, final Player winner) {
        if (move.isEndGameMove()) {
            return move.getOwner();
        }
        return winner;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class MatchBuilder {


    }
}
