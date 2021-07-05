package com.sixshop.sixspace.slack.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sixshop.sixspace.slack.repository.dto.SlackMessage;

public interface SlackNotifier {

    void send(final String url, final SlackMessage slackMessage);
}
