package com.lex4hex.kalah.repository;

import com.lex4hex.kalah.exception.GameNotFoundException;
import com.lex4hex.kalah.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to store games.
 */
@Repository
public class GameRepository {
    private final Map<UUID, Game> games = new ConcurrentHashMap<>(10);

    public void saveGame(UUID id, Game game) {
        games.put(id, game);
    }

    public Game findGame(UUID id) {
        if (!games.containsKey(id)) {
            throw new GameNotFoundException(id.toString());
        }

        return games.get(id);
    }
}
