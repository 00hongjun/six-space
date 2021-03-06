package com.sixshop.sixspace.slack.presentation;

import com.sixshop.sixspace.slack.domain.SlackVacationCommand;
import com.sixshop.sixspace.slack.presentation.dto.SlackCommandRequest;
import com.sixshop.sixspace.slack.service.SlackWebHookService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("hooks/slack/vacation")
public class SlackWebHookController {

    private final SlackWebHookService slackWebHookService;

    @PostMapping
    public void saveVacationBySlackCommand(@RequestBody @Valid final SlackCommandRequest request) {
        slackWebHookService.notify(request.getSlackId(), new SlackVacationCommand(request.getCommandMessage()));
    }
}
