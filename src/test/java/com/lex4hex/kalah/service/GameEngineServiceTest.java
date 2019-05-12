package com.lex4hex.kalah.service;

import com.lex4hex.kalah.dto.CreatedGame;
import com.lex4hex.kalah.dto.GameInformation;
import com.lex4hex.kalah.game_chain.GameConstraints;
import com.lex4hex.kalah.game_chain.KalahChain;
import com.lex4hex.kalah.game_chain.MoveStones;
import com.lex4hex.kalah.model.Game;
import com.lex4hex.kalah.model.GameStatus;
import com.lex4hex.kalah.model.Pit;
import com.lex4hex.kalah.model.Player;
import com.lex4hex.kalah.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GameEngineServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameConstraints gameConstraints;

    @Mock
    private MoveStones moveStones;

    @Spy
    private List<KalahChain> chainList = new ArrayList<>();

    @InjectMocks
    private GameEngineService uut;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        chainList.add(gameConstraints);
        chainList.add(moveStones);
    }

    @Test
    void createGame() {
        final CreatedGame game = uut.createGame();
        Mockito.verify(gameRepository).saveGame(any(), any());
        assertNotNull(game);
    }

    @Test
    void makeMove() {
        final UUID gameId = UUID.randomUUID();

        final Game game = new Game();
        game.setGameStatus(GameStatus.FIRST_PLAYER_TURN);
        final Pit pit = game.getBoard().getPits().get(1);

        when(gameRepository.findGame(eq(gameId))).thenReturn(game);
        when(gameConstraints.process(any(), any())).thenReturn(pit);
        when(moveStones.process(any(), any())).thenReturn(pit);

        final GameInformation gameInformation = uut.makeMove(gameId, 1);

        Mockito.verify(moveStones).process(eq(game), eq(pit));
        Mockito.verify(gameConstraints).process(eq(game), eq(pit));

        assertEquals(GameStatus.FIRST_PLAYER_TURN, gameInformation.getGameStatus());
        assertEquals(game.getUri().toString(), gameInformation.getUri());
    }
}