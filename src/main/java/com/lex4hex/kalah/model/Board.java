package com.lex4hex.kalah.model;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Represents 6 stone Kalah game board.
 * It contains 14 {@link Pit} where 7 and 14 are first and second players Kalahs respectively.
 */
public class Board {
    public static final Integer FIRST_PIT_INDEX = 1;
    public static final Integer LAST_PIT_INDEX = 14;
    public static final Integer PLAYER1_KALAH = 7;
    public static final Integer PLAYER2_KALAH = 14;

    /**
     * Map of {@link Pit} where key is pit's index
     */
    @Getter
    private Map<Integer, Pit> pits;

    /**
     * Initializes Kalah game board
     */
    Board() {
        this.pits = new ConcurrentHashMap<>(14);

        // Run through each board index and create pits with initial stones amount
        for (int i = FIRST_PIT_INDEX; i < LAST_PIT_INDEX; i++) {
            if (i < PLAYER1_KALAH) {
                pits.put(i, new Pit(i, Pit.INITIAL_STONES_COUNT, Player.PLAYER_1));
                continue;
            }

            if (i > PLAYER1_KALAH) {
                pits.put(i, new Pit(i, Pit.INITIAL_STONES_COUNT, Player.PLAYER_2));
            }
        }

        // Player's kalahs are empty
        pits.put(Board.PLAYER1_KALAH, new Pit(Board.PLAYER1_KALAH, 0, Player.PLAYER_1));
        pits.put(Board.PLAYER2_KALAH, new Pit(Board.PLAYER2_KALAH, 0, Player.PLAYER_2));
    }

    Map<Integer, Integer> getBoardStatus() {
        return pits.values().stream().collect(Collectors.toMap(Pit::getIndex, Pit::getStonesCount));
    }
}
