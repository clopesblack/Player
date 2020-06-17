package com.challenge.player.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Slf4j
@ControllerAdvice
public class PlayerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TaskRejectedException.class})
    public ResponseEntity<Object> handleMaxThreadException(final TaskRejectedException exception) {
        log.info("msg={}, e={}", "Too many matches occurring.", exception.getMessage());
        return ResponseEntity.status(TOO_MANY_REQUESTS).body("Maximum number of simultaneous matches reached");
    }
}
