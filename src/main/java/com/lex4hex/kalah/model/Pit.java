package com.lex4hex.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a pit. Each pit could contain stones or be empty.
 * Each pit is assigned to {@link Player}
 * Index range is determined in {@link Board} constants
 */
@Data
@AllArgsConstructor
public class Pit {

    /**
     * Initial amount of stones places in each pit except kalahs
     */
    public static final int INITIAL_STONES_COUNT = 6;

    /**
     * Index range is determined in {@link Board} constants
     */
    private Integer index;

    private Integer stonesCount;

    private Player player;

    public boolean isKalah() {
        return this.index.equals(Board.PLAYER1_KALAH) || this.index.equals(Board.PLAYER2_KALAH);
    }
}
