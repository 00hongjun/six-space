package com.sixshop.sixspace.slack.repository;

import com.sixshop.sixspace.slack.repository.dto.SlackMessage;

public interface SlackNotifier {

    void send(final String url, final SlackMessage slackMessage);
}
