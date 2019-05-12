package com.lex4hex.kalah.service;

import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PitService {

    private final GameService gameService;

    /**
     * Looks for the next available pit.
     * Pit is available if it's not the opponent player's kalah.
     *
     * @param pit  Pit to search next available from
     * @param game Current game
     * @return next available pit
     */
    public Pit getNextAvailablePit(Pit pit, Game game) {
        int nextPitIndex = pit.getIndex() + 1;

        // Cycle through board
        if (nextPitIndex > Board.LAST_PIT_INDEX) {
            nextPitIndex = 1;
        }

        final Pit nextPit = game.getBoard().getPits().get(nextPitIndex);

        if (isOtherPlayerKalah(nextPit, game)) {
            return getNextAvailablePit(nextPit, game);
        }

        return nextPit;
    }

    /**
     * Determines if provided {@link Pit} is another player's kalah
     *
     * @param pit  Pit to check
     * @param game Current game
     */
    private boolean isOtherPlayerKalah(Pit pit, Game game) {
        if (pit.getIndex().equals(Board.PLAYER1_KALAH) && gameService.getActivePlayer(game) != Player.PLAYER_1) {
            return true;
        }

        return pit.getIndex().equals(Board.PLAYER2_KALAH) &&
                gameService.getActivePlayer(game) != Player.PLAYER_2;
    }
}
