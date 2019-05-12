package com.lex4hex.kalah.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String id) {
        super(ErrorType.GAME_NOT_FOUND + id);
    }
}
