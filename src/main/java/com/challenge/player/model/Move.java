package com.challenge.player.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Getter
@JsonDeserialize(builder = Move.MoveBuilder.class)
@Builder(builderClassName = "MoveBuilder", toBuilder = true)
@RequiredArgsConstructor(access = PRIVATE)
public class Move {

    private final Integer value;

    public Move nextMove() {
        if (isValueDivisibleByThree(0)) {
            return getNewMoveDividingByThree(0);
        }
        if (isValueDivisibleByThree(1)) {
            return getNewMoveDividingByThree(1);
        }
        return getNewMoveDividingByThree(-1);
    }

    public boolean isFinished() {
        return this.value.equals(1);
    }

    private Move getNewMoveDividingByThree(final Integer addition) {
        log.info("originalValue={}, addition={}, msg={}", this.value, addition, "Operation being applied");
        final Move move = Move.builder()
                .value((this.value + addition) / 3)
                .build();
        log.info("value={}, msg={}", move.value, "A new move was made");
        return move;
    }

    private boolean isValueDivisibleByThree(final Integer addition) {
        return ((this.value + addition) % 3 == 0);
    }

    private static int getRandomValue() {
        return ThreadLocalRandom.current().nextInt(3, Integer.MAX_VALUE);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class MoveBuilder {

        public Move build() {
            if (null == this.value) {
                this.value = getRandomValue();
            }
            return new Move(this.value);
        }
    }
}

