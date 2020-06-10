package com.challenge.player.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

@ResponseStatus(value = OK)
public class OpponentNotAvailableException extends RuntimeException {

    public OpponentNotAvailableException() {
        super("The match was not created - Opponent not available to play. Try again later!");
    }
}
