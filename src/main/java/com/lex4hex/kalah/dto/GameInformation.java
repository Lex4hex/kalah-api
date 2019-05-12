package com.lex4hex.kalah.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * DTO. Represents current game status after move.
 */
@AllArgsConstructor
@ApiModel(description = "Represents current game status after move.")
@Getter
public class GameInformation {
    @ApiModelProperty("Unique game identifier")
    @JsonProperty
    private String id;

    @ApiModelProperty("Link to current game")
    @JsonProperty
    private String uri;

    @ApiModelProperty("Game status")
    @JsonProperty
    private com.lex4hex.kalah.model.GameStatus gameStatus;

    @ApiModelProperty("Status of the game board. Key is pit index and value is stones count in this pit.")
    @JsonProperty("status")
    private Map<Integer, Integer> boardStatus;
}
