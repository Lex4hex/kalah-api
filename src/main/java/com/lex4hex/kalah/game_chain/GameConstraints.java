package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.exception.ErrorType;
import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Checks game constraints: player's turns, pit's position and owner of pit validity.
 */
@Component
@Order(1)
@Slf4j
public class GameConstraints implements KalahChain {

    @Override
    public Pit process(Game game, Pit currentPit) {
        log.info("Game: {}. Checking game rules constants", game.getId());

        Player player = initFirstPlayer(game, currentPit);

        checkPlayersTurn(game, player);
        log.info("Game: {}. Selected player {} is OK", game.getId(), player);

        checkPit(currentPit, player);
        log.info("Game: {}. Selected pit with id {} is OK", game.getId(), currentPit.getIndex());

        return currentPit;
    }

    /**
     * When game is created first turn determined by the owner of the first pit being sowed
     *
     * @param game       Current game
     * @param currentPit Selected pit
     * @return Selected first player
     */
    private Player initFirstPlayer(Game game, Pit currentPit) {
        if (game.getGameStatus() == GameStatus.STARTED) {
            switch (currentPit.getPlayer()) {
                case PLAYER_1:
                    game.setGameStatus(GameStatus.FIRST_PLAYER_TURN);
                    break;
                case PLAYER_2:
                    game.setGameStatus(GameStatus.SECOND_PLAYER_TURN);
                    break;
            }
        }

        return currentPit.getPlayer();
    }

    /**
     * Checks that selected pit is in rage of {@link Game}'s {@link Board} and {@link Player} is allowed to sow this
     * {@link Pit}
     *
     * @param currentPit pit to sow
     */
    private void checkPit(Pit currentPit, Player player) {
        if (currentPit.getIndex() < Board.FIRST_PIT_INDEX || currentPit.getIndex() > Board.LAST_PIT_INDEX) {
            throw new KalahException(ErrorType.OUT_OF_BOARD);
        }

        if (currentPit.isKalah()) {
            throw new KalahException(ErrorType.CANT_SOW_FROM_KALAH);
        }

        if (currentPit.getPlayer() != player) {
            throw new KalahException(ErrorType.ANOTHER_PLAYER_PIT);
        }
    }

    /**
     * Checks that provided player is allowed to make move in current game state
     *
     * @param game   Current game
     * @param player Current player
     */
    private void checkPlayersTurn(Game game, Player player) {
        if (game.getGameStatus() != GameStatus.FIRST_PLAYER_TURN &&
                game.getGameStatus() != GameStatus.SECOND_PLAYER_TURN) {
            throw new KalahException(ErrorType.GAME_FINISHED);
        }

        if (player == Player.PLAYER_1 && (game.getGameStatus() != GameStatus.FIRST_PLAYER_TURN)) {
            throw new KalahException(ErrorType.ANOTHER_PLAYER_TURN);
        }

        if (player == Player.PLAYER_2 && (game.getGameStatus() != GameStatus.SECOND_PLAYER_TURN)) {
            throw new KalahException(ErrorType.ANOTHER_PLAYER_TURN);
        }
    }
}
