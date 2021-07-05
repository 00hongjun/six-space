package com.sixshop.sixspace.slack.service;

import com.sixshop.sixspace.exception.IllegalFormatSlackCommandException;
import com.sixshop.sixspace.exception.NotEnoughVacationException;
import com.sixshop.sixspace.slack.domain.SlackVacationCommand;
import com.sixshop.sixspace.slack.repository.SlackNotifier;
import com.sixshop.sixspace.slack.repository.dto.SlackMessage;
import com.sixshop.sixspace.user.application.UserService;
import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.Vacations;
import com.sixshop.sixspace.vacation.repository.DayOfMonthVacationRepository;
import com.sixshop.sixspace.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SlackWebHookService {

    @Value("${spring.slack.webhook.url}")
    private String webHookUrl;

    private final UserService userService;
    private final VacationRepository vacationRepository;
    private final DayOfMonthVacationRepository dayOfMonthVacationRepository;
    private final SlackNotifier slackNotifier;

    @Transactional
    public void notify(final String slackId, final String commandMessage) {
        try {
            final SlackVacationCommand command = new SlackVacationCommand(commandMessage);
            final User user = userService.findUserBySlackId(slackId);

            Vacations vacations = Vacations.of(dayOfMonthVacationRepository.findAllByUserId(user.getId()));
            int totalUseHour = vacations.bringTotalUseHour();
            int remain = user.getTotalVacationTime() - totalUseHour - command.getUseHour();
            if (remain < 0) {
                throw new NotEnoughVacationException();
            }

            vacationRepository.save(new Vacation(user.getId(), command.getStartTime(), command.getUseHour()));

            slackNotifier.send(webHookUrl, SlackMessage.success(slackId, command.getStartTime().getTime(), command.getUseHour()));
        } catch (IllegalFormatSlackCommandException | NotEnoughVacationException exception) {
            slackNotifier.send(webHookUrl, SlackMessage.fail(slackId, exception.getMessage()));
        }
    }
}
