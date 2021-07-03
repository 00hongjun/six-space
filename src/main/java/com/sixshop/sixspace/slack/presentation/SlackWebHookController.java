package com.sixshop.sixspace.slack.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{slack-user-id}/vacation")
public class SlackWebHookController {

    // private final SlackWebHookService slackWebHookService;

    // /휴가 {오늘/내일} 09:00 8
    // 7/1 (월) 17시 10분부터 2시간 휴가 사용합니당
    @PostMapping
    public void saveVacationBySlackCommand(@PathVariable("slack-user-id") long slackUserId, @RequestBody final String message) {
        // slackWebHookService.send(slackUserId, message);
    }
}
