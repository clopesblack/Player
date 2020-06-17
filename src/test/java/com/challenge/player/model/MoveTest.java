package com.challenge.player.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    @Test
    public void isEndGameMove_ShouldReturnTrueWithValueOne() {
        final Move move = Move.builder().value(1).owner(getOwner()).build();
        assertTrue(move.isEndGameMove());
    }

    @Test
    public void isEndGameMove_ShouldReturnFalseWithValueDifferentFromOne() {
        final Move move = Move.builder().value(90).owner(getOwner()).build();
        assertFalse(move.isEndGameMove());
    }

    @Test
    public void build_ShouldGenerateValueWhenNotPassedWithBuildMove() {
        final Move move = Move.builder().owner(getOwner()).build();
        assertNotNull(move.getValue());
    }

    @Test
    public void nextMove_ShouldAdditionPlusOneToMakeValueDivisibleBy3() {
        final Move move = Move.builder().value(56).owner(getOwner()).build();
        assertEquals(19, move.nextMove().getValue());
    }

    @Test
    public void nextMove_ShouldAdditionZeroToMakeValueDivisibleBy3() {
        final Move move = Move.builder().value(6).owner(getOwner()).build();
        assertEquals(2, move.nextMove().getValue());
    }

    @Test
    public void nextMove_ShouldAdditionMinusOneToMakeValueDivisibleBy3() {
        final Move move = Move.builder().value(19).owner(getOwner()).build();
        assertEquals(6, move.nextMove().getValue());
    }

    private Player getOwner() {
        return Player.builder().address("").build();
    }
}
