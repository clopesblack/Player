package com.challenge.player.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.OK;

@ResponseStatus(value = OK)
public class OpponentNotAvailableToPlayException extends RuntimeException {

    public OpponentNotAvailableToPlayException() {
        super("The match was not created - Opponent not available to play. Try again later!");
    }
}
