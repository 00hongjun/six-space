package com.sixshop.sixspace.slack.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlackCommandRequest {

    @JsonProperty("user_id")
    @NotNull
    private String slackId;

    @JsonProperty("text")
    @NotNull
    private String commandMessage;
}
