package com.lex4hex.kalah.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a game. Contains a {@link Board} with it's state.
 */
public class Game {
    /**
     * Unique game identifier
     */
    @Getter
    private UUID id;

    /**
     * Link to current game
     */
    @Getter
    private URI uri;

    @Getter
    private Board board;

    @Getter
    @Setter
    private GameStatus gameStatus;

    /**
     * Initializes a new game with unique identifier and link to the game.
     * Also constructs game's {@link Board} with initial stones distributed in pits
     */
    public Game() {
        id = UUID.randomUUID();
        uri = URI.create("/games/" + getId());
        board = new Board();
        gameStatus = GameStatus.STARTED;
    }

    /**
     * @return Status of game board. Key is {@link Pit} index and value is stones count in this {@link Pit}.
     */
    public Map<Integer, Integer> getStatus() {
        return board.getBoardStatus();
    }
}
