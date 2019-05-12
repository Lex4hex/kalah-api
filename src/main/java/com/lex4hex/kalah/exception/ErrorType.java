package com.lex4hex.kalah.exception;

import com.lex4hex.kalah.model.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Stores all error types with messages.
 * See {@link KalahException}
 */
@AllArgsConstructor
public enum ErrorType {
    EMPTY_PIT("Pit is empty. You can sow only from non-empty pits"),
    GAME_NOT_FOUND("No game with id "),
    CANT_SOW_FROM_KALAH("Can't sow from kalahs"),
    ANOTHER_PLAYER_PIT("Can't sow from another player's pit"),
    OUT_OF_BOARD("Pit are located in rage from " + Board.FIRST_PIT_INDEX + " to " + Board.LAST_PIT_INDEX),
    ANOTHER_PLAYER_TURN("It's another player's turn"),
    GAME_FINISHED("Game is finished");

    @Getter
    private final String message;
}
