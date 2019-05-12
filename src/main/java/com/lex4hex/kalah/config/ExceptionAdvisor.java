package com.lex4hex.kalah.config;

import com.lex4hex.kalah.exception.GameNotFoundException;
import com.lex4hex.kalah.exception.KalahException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

/**
 * Describes handling logic of exceptions that could be thrown from controller
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvisor {

    @ExceptionHandler(KalahException.class)
    public final ResponseEntity handleKalahException(final KalahException e) {
        log.error(e.getLocalizedMessage(), e);
        return badRequest().body(e.getLocalizedMessage());
    }

    @ExceptionHandler(GameNotFoundException.class)
    public final ResponseEntity handleNotFound(final GameNotFoundException e) {
        log.error(e.getLocalizedMessage(), e);
        return status(NOT_FOUND).body(e.getLocalizedMessage());
    }

}
