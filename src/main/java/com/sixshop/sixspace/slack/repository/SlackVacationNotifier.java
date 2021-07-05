package com.sixshop.sixspace.slack.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixshop.sixspace.exception.SlackWebHookFailException;
import com.sixshop.sixspace.slack.repository.dto.SlackMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SlackVacationNotifier implements SlackNotifier {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SlackVacationNotifier(final RestTemplate restTemplate,
        final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(final String webHookUrl, final SlackMessage slackMessage) {
        try {
            restTemplate.postForEntity(webHookUrl, objectMapper.writeValueAsString(slackMessage), String.class);
        } catch (RestClientException | JsonProcessingException e) {
            throw new SlackWebHookFailException(e.getMessage());
        }
    }
}
