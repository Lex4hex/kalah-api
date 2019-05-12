package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.exception.ErrorType;
import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameConstraintsTest {
    private GameConstraints uut = new GameConstraints();

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(new Pit(1, 0, Player.PLAYER_1), GameStatus.SECOND_PLAYER_TURN, new KalahException(),
                        ErrorType.ANOTHER_PLAYER_TURN),
                Arguments.of(new Pit(8, 0, Player.PLAYER_2), GameStatus.FIRST_PLAYER_TURN, new KalahException(),
                        ErrorType.ANOTHER_PLAYER_TURN),
                Arguments.of(new Pit(-1, 0, null), GameStatus.FIRST_PLAYER_TURN,
                        new KalahException(), ErrorType.OUT_OF_BOARD),
                Arguments.of(new Pit(Board.PLAYER1_KALAH, 0, null), GameStatus.FIRST_PLAYER_TURN,
                        new KalahException(), ErrorType.CANT_SOW_FROM_KALAH),
                Arguments.of(new Pit(Board.PLAYER2_KALAH, 0, null), GameStatus.FIRST_PLAYER_TURN,
                        new KalahException(), ErrorType.CANT_SOW_FROM_KALAH),
                Arguments.of(new Pit(Board.PLAYER2_KALAH, 0, null), GameStatus.FIRST_PLAYER_WINNER,
                        new KalahException(), ErrorType.GAME_FINISHED)
        );
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void processThrows(Pit pit, GameStatus gameStatus, KalahException exception, ErrorType errorType) {
        final Game game = new Game();
        game.setGameStatus(gameStatus);

        final KalahException kalahException = assertThrows(exception.getClass(), () -> uut.process(game, pit));
        assertEquals(errorType, kalahException.getErrorType());
    }

    @Test
    void processesRightConstraints() {
        final Game game = new Game();

        uut.process(game, game.getBoard().getPits().get(1));
    }
}