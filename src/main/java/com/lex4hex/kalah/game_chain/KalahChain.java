package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;

/**
 * Chain of game actions. Implementation processing order is defined by
 * {@link org.springframework.core.annotation.Order} annotation
 */
public interface KalahChain {

    /**
     * Process the game chain element. Could be any rule specific action or some coupling modification.
     *
     * @param game       Current game
     * @param currentPit Last pit where player sowed a stone
     * @return New last sowed pit. Should be passed for each chain element
     */
    Pit process(Game game, Pit currentPit) throws KalahException;
}
