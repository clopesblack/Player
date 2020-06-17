package com.challenge.player.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ResponseStatus(value = SERVICE_UNAVAILABLE)
public class OpponentNotAvailableToPlayException extends RuntimeException {

    public OpponentNotAvailableToPlayException() {
        super("The match was not created - The opponent is not available to play. Try again later!");
    }
}
