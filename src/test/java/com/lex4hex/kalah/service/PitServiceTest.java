package com.lex4hex.kalah.service;

import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

class PitServiceTest {

    @InjectMocks
    private PitService uut;

    @Mock
    private GameService gameService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    private static Stream<Arguments> getArguments() {
        return Stream.of(
                // First player
                Arguments.of(1,2, Player.PLAYER_1),
                Arguments.of(2,3, Player.PLAYER_1),
                Arguments.of(6,7, Player.PLAYER_1),
                Arguments.of(8,9, Player.PLAYER_1),

                // Skips kalah
                Arguments.of(13,1, Player.PLAYER_1),

                //Second player
                Arguments.of(8,9, Player.PLAYER_2),
                Arguments.of(9,10, Player.PLAYER_2),
                Arguments.of(13,14, Player.PLAYER_2),
                Arguments.of(1,2, Player.PLAYER_2),

                // Skips kalah
                Arguments.of(6,8, Player.PLAYER_2)
        );
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void getNextAvailablePit(int currentPitIndex, int expectedNextIndex, Player player) {
        final Game game = new Game();

        when(gameService.getActivePlayer(game)).thenReturn(player);

        final Pit nextAvailablePit = uut.getNextAvailablePit(game.getBoard().getPits().get(currentPitIndex), game);

        assertEquals(expectedNextIndex, (int) nextAvailablePit.getIndex());
    }
}