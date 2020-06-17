package com.challenge.player.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Slf4j
@ControllerAdvice
public class PlayerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TaskRejectedException.class})
    public ResponseEntity<Object> handleMaxThreadException(final TaskRejectedException exception) {
        log.info("msg={}, e={}", "Too many matches occurring.", exception.getMessage());
        ApiError apiError = ApiError.builder()
                .status(TOO_MANY_REQUESTS)
                .message("Maximum number of simultaneous matches reached")
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({OpponentNotAvailableToPlayException.class})
    public ResponseEntity<Object> handleOpponentNotAvailable(final OpponentNotAvailableToPlayException exception) {
        log.info("msg={}, e={}", "Opponent not Available", exception.getMessage());
        ApiError apiError = ApiError.builder()
                .status(SERVICE_UNAVAILABLE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
