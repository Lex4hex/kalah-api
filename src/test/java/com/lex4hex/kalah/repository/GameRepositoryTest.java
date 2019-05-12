package com.lex4hex.kalah.repository;

import com.lex4hex.kalah.exception.GameNotFoundException;
import com.lex4hex.kalah.model.Game;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameRepositoryTest {

    private GameRepository uut = new GameRepository();

    @Test
    void findGame() {
        final Game savedGame = new Game();
        final UUID uuid = UUID.randomUUID();

        uut.saveGame(uuid, savedGame);

        final Game foundGame = uut.findGame(uuid);
        assertEquals(savedGame, foundGame);
    }

    @Test
    void findGameThrows() {
        final UUID uuid = UUID.randomUUID();

        assertThrows(GameNotFoundException.class, () -> uut.findGame(uuid));
    }
}