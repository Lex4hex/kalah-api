package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EndGameTest {

    private EndGame uut = new EndGame();

    private static Stream<Arguments> playersArguments() {
        return Stream.of(
                Arguments.of(Player.PLAYER_2, GameStatus.FIRST_PLAYER_WINNER),
                Arguments.of(Player.PLAYER_1, GameStatus.SECOND_PLAYER_WINNER)
        );
    }

    @ParameterizedTest
    @MethodSource("playersArguments")
    void playerWins(Player player, GameStatus winner) {
        final Game game = new Game();

        game.getBoard().getPits().forEach(((integer, pit) -> {
            if (pit.getPlayer() == player) {
                pit.setStonesCount(0);
            }
        }));

        uut.process(game, game.getBoard().getPits().get(1));

        assertEquals(winner, game.getGameStatus());
    }

    @Test
    void tie() {
        final Game game = new Game();

        game.getBoard().getPits().forEach(((integer, pit) -> {
            if (!pit.isKalah()) {
                pit.setStonesCount(0);
            }
        }));

        game.getBoard().getPits().get(Board.PLAYER1_KALAH).setStonesCount(36);
        game.getBoard().getPits().get(Board.PLAYER2_KALAH).setStonesCount(36);

        uut.process(game, game.getBoard().getPits().get(1));

        assertEquals(GameStatus.TIE, game.getGameStatus());
    }
}