package com.lex4hex.kalah.service;

import com.lex4hex.kalah.exception.ErrorType;
import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private final GameService uut = new GameService();

    @Test
    void getActivePlayer() {
        final Game game = new Game();
        game.setGameStatus(GameStatus.FIRST_PLAYER_TURN);

        final Player activePlayer = uut.getActivePlayer(game);

        assertEquals(Player.PLAYER_1, activePlayer);
    }

    @Test
    void getActivePlayerThrows() {
        final Game game = new Game();
        game.setGameStatus(GameStatus.FIRST_PLAYER_WINNER);

        final KalahException kalahException = assertThrows(KalahException.class, () -> uut.getActivePlayer(game));
        assertEquals(ErrorType.GAME_FINISHED, kalahException.getErrorType());
    }

    @Test
    void getActivePlayerKalah() {
        final Game game = new Game();
        game.setGameStatus(GameStatus.FIRST_PLAYER_TURN);

        final Pit kalah = uut.getActivePlayerKalah(game);

        assertEquals(game.getBoard().getPits().get(7), kalah);
    }

    @Test
    void getActivePlayerKalahThrows() {
        final Game game = new Game();
        game.setGameStatus(GameStatus.TIE);

        final KalahException kalahException = assertThrows(KalahException.class, () -> uut.getActivePlayerKalah(game));
        assertEquals(ErrorType.GAME_FINISHED, kalahException.getErrorType());
    }
}