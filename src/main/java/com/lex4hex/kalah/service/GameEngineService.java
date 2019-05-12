package com.lex4hex.kalah.service;

import com.lex4hex.kalah.dto.CreatedGame;
import com.lex4hex.kalah.dto.GameInformation;
import com.lex4hex.kalah.exception.KalahException;
import com.lex4hex.kalah.game_chain.KalahChain;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.repository.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Main game service which creates and runs a game.
 */
@Service
@AllArgsConstructor
@Slf4j
public class GameEngineService {

    private final GameRepository gameRepository;
    private final List<KalahChain> kalahChains;

    /**
     * Initializes a new game and stores it into repository.
     *
     * @return Created game info DTO.
     */
    public CreatedGame createGame() {
        Game game = new Game();

        gameRepository.saveGame(game.getId(), game);
        log.info("Created and saved a new game with id = {}", game.getId());

        return new CreatedGame(game.getId().toString(), game.getUri().toString());
    }

    /**
     * Runs a chain {@link KalahChain} of game rules and processing logic.
     *
     * @param gameId Unique game id
     * @param pitId  Selected pit to sow from
     * @return Game status DTO with info about the game and board status
     * @throws KalahException in case of wrong provided data or game logic validation errors
     */
    public GameInformation makeMove(UUID gameId, Integer pitId) throws KalahException {
        final Game game = gameRepository.findGame(gameId);

        Pit currentPit = game.getBoard().getPits().get(pitId);

        for (KalahChain kalahChain : kalahChains) {
            currentPit = kalahChain.process(game, currentPit);
        }

        return new GameInformation(game.getId().toString(), game.getUri().toString(), game.getGameStatus(),
                game.getStatus());
    }
}
