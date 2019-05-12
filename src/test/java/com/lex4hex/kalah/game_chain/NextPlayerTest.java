package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Player;
import com.lex4hex.kalah.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

class NextPlayerTest {
    @InjectMocks
    private NextPlayer uut;

    @Mock
    private GameService gameService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void secondTurnRule() {
        final Game game = new Game();
        game.setGameStatus(GameStatus.FIRST_PLAYER_TURN);
        when(gameService.getActivePlayerKalah(eq(game)))
                .thenReturn(game.getBoard().getPits().get(Board.PLAYER1_KALAH));
        when(gameService.getActivePlayer(eq(game))).thenReturn(Player.PLAYER_1);

        uut.process(game, game.getBoard().getPits().get(Board.PLAYER1_KALAH));

        assertEquals(GameStatus.FIRST_PLAYER_TURN, game.getGameStatus());
    }

    @Test
    void switchTurn() {
        final Game game = new Game();

        when(gameService.getActivePlayerKalah(eq(game)))
                .thenReturn(game.getBoard().getPits().get(Board.PLAYER1_KALAH));
        when(gameService.getActivePlayer(eq(game))).thenReturn(Player.PLAYER_1);

        uut.process(game, game.getBoard().getPits().get(Board.PLAYER1_KALAH - 1));

        assertEquals(GameStatus.SECOND_PLAYER_TURN, game.getGameStatus());
    }
}