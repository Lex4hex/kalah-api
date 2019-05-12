package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
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

class LastPitTest {

    @InjectMocks
    private LastPit uut;

    @Mock
    private GameService gameService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void captures() {
        final Game game = new Game();

        when(gameService.getActivePlayer(eq(game))).thenReturn(Player.PLAYER_1);
        when(gameService.getActivePlayerKalah(eq(game)))
                .thenReturn(game.getBoard().getPits().get(Board.PLAYER1_KALAH));

        final Pit pit = game.getBoard().getPits().get(1);
        pit.setStonesCount(1);

        uut.process(game, pit);

        assertEquals(0, (int) pit.getStonesCount());
        assertEquals(0, (int) game.getBoard().getPits().get(13).getStonesCount());
        assertEquals(7, (int) game.getBoard().getPits().get(Board.PLAYER1_KALAH).getStonesCount());
    }
}