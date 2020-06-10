package com.challenge.player.exception;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@ControllerAdvice
public class PlayerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({TaskRejectedException.class})
    public ResponseEntity<Object> handleMaxThreadException(final TaskRejectedException exception) {
        return new ResponseEntity<>(
                "Maximum number of simultaneous matches reached", new HttpHeaders(), TOO_MANY_REQUESTS);
    }
}
