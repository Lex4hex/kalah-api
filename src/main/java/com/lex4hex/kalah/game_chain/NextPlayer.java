package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import com.lex4hex.kalah.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Determines next player based on game and board status.
 * Player has another turn if last stone he/she sowed was in own kalah.
 */
@Component
@Order(4)
@AllArgsConstructor
@Slf4j
public class NextPlayer implements KalahChain {

    private final GameService gameService;

    @Override
    public Pit process(Game game, Pit currentPit) throws KalahException {
        log.info("Game: {}. Determining next player", game.getId());

        // Change turn only if last stone placed not in player's kalah
        if (!gameService.getActivePlayerKalah(game).equals(currentPit)) {
            final Player activePlayer = gameService.getActivePlayer(game);

            // Switch players
            switch (activePlayer) {
                case PLAYER_1:
                    game.setGameStatus(GameStatus.SECOND_PLAYER_TURN);
                    break;
                case PLAYER_2:
                    game.setGameStatus(GameStatus.FIRST_PLAYER_TURN);
                    break;
            }
        }

        log.info("Game: {}. Next player is {}", game.getId(), gameService.getActivePlayer(game));

        return currentPit;
    }
}
