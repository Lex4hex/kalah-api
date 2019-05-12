package com.lex4hex.kalah.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lex4hex.kalah.model.Game;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;

/**
 * DTO. Represents created game info from {@link Game}
 */
@AllArgsConstructor
@ApiModel(description = "Represents created game information")
public class CreatedGame {
    @ApiModelProperty("Unique game identifier")
    @JsonProperty
    private String id;

    @ApiModelProperty("Link to the current game")
    @JsonProperty
    private String uri;
}
