package com.lex4hex.kalah.controller;

import com.lex4hex.kalah.dto.CreatedGame;
import com.lex4hex.kalah.dto.GameInformation;
import com.lex4hex.kalah.service.GameEngineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Api("Provides endpoints for interactions with the game")
public class GameController {

    private GameEngineService gameEngineService;

    @ApiOperation(value = "Creates a new game and persists it to storage. Initializes game board with initial stones " +
            "in each of players pits.")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 500, message = "Unexpected server error")
    })
    public CreatedGame createGame() {
        return gameEngineService.createGame();
    }

    @ApiOperation(value = "Makes a move. Sows stones from selected pit.")
    @PutMapping("/{gameId}/pits/{pitId}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Wrong input parameters provided"),
            @ApiResponse(code = 500, message = "Unexpected server error")
    })
    public GameInformation makeMove(
            @ApiParam("Generated unique game identifier") @PathVariable @NotNull UUID gameId,
            @ApiParam("Pit index to sow stones from. From 1 to 6 for the first player. From 8 to 13 for the second " +
                    "player") @PathVariable @NotNull Integer pitId) {
        return gameEngineService.makeMove(gameId, pitId);
    }
}
