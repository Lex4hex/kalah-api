package com.lex4hex.kalah.service;

import com.lex4hex.kalah.exception.ErrorType;
import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import org.springframework.stereotype.Service;

/**
 * Provides game logic specific functionality
 */
@Service
public class GameService {

    /**
     * Gets active player of provided game
     *
     * @param game Current game
     * @return Current player whose turn it is
     */
    public Player getActivePlayer(Game game) {
        switch (game.getGameStatus()) {
            case FIRST_PLAYER_TURN:
                return Player.PLAYER_1;
            case SECOND_PLAYER_TURN:
                return Player.PLAYER_2;
            default:
                throw new KalahException(ErrorType.GAME_FINISHED);
        }
    }

    /**
     * Gets a kalah {@link Pit} of active player
     *
     * @param game Current game
     * @return Current player's kalah
     */
    public Pit getActivePlayerKalah(Game game) {
        switch (game.getGameStatus()) {
            case FIRST_PLAYER_TURN:
                return game.getBoard().getPits().get(Board.PLAYER1_KALAH);
            case SECOND_PLAYER_TURN:
                return game.getBoard().getPits().get(Board.PLAYER2_KALAH);
            default:
                throw new KalahException(ErrorType.GAME_FINISHED);
        }
    }
}
