package com.lex4hex.kalah.game_chain;

import com.lex4hex.kalah.model.Board;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import com.lex4hex.kalah.service.PitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MoveStonesTest {

    @InjectMocks
    private MoveStones uut;

    @Mock
    private PitService pitService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void moves() {
        final Game game = new Game();

        when(pitService.getNextAvailablePit(any(), any()))
                .thenReturn(game.getBoard().getPits().get(2))
                .thenReturn(game.getBoard().getPits().get(3))
                .thenReturn(game.getBoard().getPits().get(4))
                .thenReturn(game.getBoard().getPits().get(5))
                .thenReturn(game.getBoard().getPits().get(6))
                .thenReturn(game.getBoard().getPits().get(7));

        final Pit startPit = game.getBoard().getPits().get(1);
        final Pit currentPit = uut.process(game, startPit);

        assertEquals(game.getBoard().getPits().get(7), currentPit);
        assertEquals(1, (int) currentPit.getStonesCount());

        for (int i = 2; i < Board.PLAYER1_KALAH; i++) {
            assertEquals(7, (int) game.getBoard().getPits().get(i).getStonesCount());
        }
    }

    @Test
    void dontPlaceInOtherPlayerKalah() {
        final Game game = new Game();
        final int startPitIndex = 6;
        final Pit startPit = game.getBoard().getPits().get(startPitIndex);
        startPit.setStonesCount(10);

        when(pitService.getNextAvailablePit(any(), any()))
                .thenReturn(game.getBoard().getPits().get(7))
                .thenReturn(game.getBoard().getPits().get(8))
                .thenReturn(game.getBoard().getPits().get(9))
                .thenReturn(game.getBoard().getPits().get(10))
                .thenReturn(game.getBoard().getPits().get(11))
                .thenReturn(game.getBoard().getPits().get(12))
                .thenReturn(game.getBoard().getPits().get(13))
                .thenReturn(game.getBoard().getPits().get(1))
                .thenReturn(game.getBoard().getPits().get(2))
                .thenReturn(game.getBoard().getPits().get(3));

        uut.process(game, startPit);

        assertEquals(0, (int) game.getBoard().getPits().get(Board.PLAYER2_KALAH).getStonesCount());
        assertEquals(0, (int) game.getBoard().getPits().get(startPitIndex).getStonesCount());
        assertEquals(1, (int) game.getBoard().getPits().get(Board.PLAYER1_KALAH).getStonesCount());

        for (int i = Board.PLAYER1_KALAH + 1; i < Board.PLAYER2_KALAH; i++) {
            assertEquals(7, (int) game.getBoard().getPits().get(i).getStonesCount());
        }

        for (int i = 1; i < 4; i++) {
            assertEquals(7, (int) game.getBoard().getPits().get(i).getStonesCount());
        }
    }
}
