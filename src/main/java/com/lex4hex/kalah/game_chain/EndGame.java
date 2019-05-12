package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Pit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Checks end game rules. Determines winner.
 * Game ends when one of the players has all empty pits.
 * Other player accumulates all of his stones in kalah.
 * Player with more stones in kalah wins.
 */
@Component
@Order(5)
@AllArgsConstructor
@Slf4j
public class EndGame implements KalahChain {

    @Override
    public Pit process(Game game, Pit currentPit) {
        log.info("Game: {}. Checking end game rules", game.getId());

        final Integer firstPlayerStonesCount = getFirstPlayerStonesCount(game.getBoard());
        final Integer secondPlayerStonesCount = getSecondPlayerStonesCount(game.getBoard());

        // If any of players have emptied all of his/her pits
        if (firstPlayerStonesCount == 0 || secondPlayerStonesCount == 0) {
            final Pit firstPlayerKalah = game.getBoard().getPits().get(Board.PLAYER1_KALAH);
            final Pit secondPlayerKalah = game.getBoard().getPits().get(Board.PLAYER2_KALAH);

            // Accumulate all players stones in their kalahs
            secondPlayerKalah.setStonesCount(secondPlayerKalah.getStonesCount() + secondPlayerStonesCount);
            firstPlayerKalah.setStonesCount(firstPlayerKalah.getStonesCount() + firstPlayerStonesCount);

            // Compare stones count
            if (firstPlayerKalah.getStonesCount() > secondPlayerKalah.getStonesCount()) {
                game.setGameStatus(GameStatus.FIRST_PLAYER_WINNER);
            } else if (firstPlayerKalah.getStonesCount() < secondPlayerKalah.getStonesCount()) {
                game.setGameStatus(GameStatus.SECOND_PLAYER_WINNER);
            } else {
                game.setGameStatus(GameStatus.TIE);
            }

            resetBoard(game.getBoard());

            log.info("Game: {} is over with status {}", game.getId(), game.getGameStatus());

            return currentPit;
        }

        log.info("Game: {} is not finished. Waiting for the next move", game.getId());

        return currentPit;
    }

    /**
     * Empties all pits of the board except kalahs.
     *
     * @param board Current game board
     */
    private void resetBoard(Board board) {
        for (int i = Board.FIRST_PIT_INDEX; i < Board.PLAYER1_KALAH; i++) {
            board.getPits().get(i).setStonesCount(0);
        }

        for (int i = Board.PLAYER1_KALAH + 1; i < Board.PLAYER2_KALAH; i++) {
            board.getPits().get(i).setStonesCount(0);
        }
    }

    private Integer getFirstPlayerStonesCount(Board board) {
        Integer count = 0;

        for (int i = Board.FIRST_PIT_INDEX; i < Board.PLAYER1_KALAH; i++) {
            count += board.getPits().get(i).getStonesCount();
        }

        return count;
    }

    private Integer getSecondPlayerStonesCount(Board board) {
        Integer count = 0;

        for (int i = Board.PLAYER1_KALAH + 1; i < Board.PLAYER2_KALAH; i++) {
            count += board.getPits().get(i).getStonesCount();
        }

        return count;
    }
}
