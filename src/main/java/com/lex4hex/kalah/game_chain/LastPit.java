package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Processes a last pick rule:
 * If the last stone is deposited into an empty pit owned by the current player and the pit opposite is not empty,
 * the player captures all of the stones in the opposite pit plus the capturing stone and moves them all to kalah
 */
@Component
@Order(3)
@AllArgsConstructor
@Slf4j
public class LastPit implements KalahChain {

    private final GameService gameService;

    @Override
    public Pit process(Game game, Pit currentPit) throws KalahException {
        log.info("Game: {}. Checking last pick rule", game.getId());

        // If it's a last player's pit and it's not a kalah
        if (gameService.getActivePlayer(game) == currentPit.getPlayer() && currentPit.getStonesCount() == 1 &&
                !currentPit.isKalah()) {
            final Pit oppositePit = getOppositePit(game, currentPit);

            final Integer oppositePitStonesCount = oppositePit.getStonesCount();

            // Capture stones of opposite pit
            if (oppositePitStonesCount > 0) {
                final Pit currentPlayerKalah = gameService.getActivePlayerKalah(game);
                currentPlayerKalah.setStonesCount(
                        currentPlayerKalah.getStonesCount() + oppositePitStonesCount + currentPit.getStonesCount());
                oppositePit.setStonesCount(0);
                currentPit.setStonesCount(0);

                log.info("Game: {}. Captured {} stones from opposite pit {}", game.getId(), oppositePitStonesCount,
                        oppositePit.getIndex());
            }
        }

        return currentPit;
    }

    /**
     * Finds opposite player's pit
     *
     * @param game       Current game
     * @param currentPit Last pit
     * @return Opposite player's pit
     */
    private Pit getOppositePit(Game game, Pit currentPit) {
        return game.getBoard().getPits()
                .get((Board.FIRST_PIT_INDEX + Board.LAST_PIT_INDEX - 1) - currentPit.getIndex());
    }
}
