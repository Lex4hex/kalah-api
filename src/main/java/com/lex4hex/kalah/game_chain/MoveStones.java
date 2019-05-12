package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.exception.ErrorType;
import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.service.PitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Main action of the game.
 * Sows all stones from selected pit.
 * On a turn, the player removes all stones from one of the pits under their control. Moving counter-clockwise, the
 * player drops one stone in each pit in turn, including the player's own kalah but not their opponent's.
 */
@Component
@Order(2)
@AllArgsConstructor
@Slf4j
public class MoveStones implements KalahChain {

    private final PitService pitService;

    @Override
    public Pit process(Game game, Pit currentPit) {
        log.info("Game: {}. Starting to sow stones from pit {}", game.getId(), currentPit.getIndex());

        // Pit should no be empty
        if (currentPit.getStonesCount() == 0) {
            throw new KalahException(ErrorType.EMPTY_PIT);
        }

        int stones = currentPit.getStonesCount();
        currentPit.setStonesCount(0);

        // Sow all available stones by one
        for (int i = stones; i > 0; i--) {
            // Select only available pits: skip opponent player's kalah and cycle though board
            final Pit nextAvailablePit = pitService.getNextAvailablePit(currentPit, game);

            nextAvailablePit.setStonesCount(nextAvailablePit.getStonesCount() + 1);
            currentPit = nextAvailablePit;
        }

        log.info("Game: {}. {} stones were distributed", game.getId(), stones);

        return currentPit;
    }
}
