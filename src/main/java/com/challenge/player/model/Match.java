package com.challenge.player.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.MDC;

import static java.util.UUID.randomUUID;

@Getter
@JsonDeserialize(builder = Match.MatchBuilder.class)
public class Match {

    private final String id;
    private final Move move;
    private final Player challenger;
    private final Player opponent;
    private final boolean finish;
    private final Player winner;

    @Builder(builderClassName = "MatchBuilder", toBuilder = true)
    private Match(final Move move, final String id, final Player opponent, final Player challenger, final Player winner) {
        this.move = move;
        this.id = createIdIfNotExists(id);
        this.opponent = opponent;
        this.challenger = challenger;
        this.finish = move.isFinished();
        this.winner = winner;
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class MatchBuilder {
    }
}
