package com.sixshop.sixspace.vacation.service;

import com.sixshop.sixspace.user.application.UserService;
import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.Vacations;
import com.sixshop.sixspace.vacation.presentation.dto.VacationResponse;
import com.sixshop.sixspace.vacation.repository.DayOfMonthVacationRepository;
import com.sixshop.sixspace.vacation.repository.VacationRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyVacationStatisticsService {

    private final VacationRepository vacationRepository;
    private final UserService userService;

    public List<Vacation> findDailyVacation(LocalDate localDate) {
        List<Vacation> allIncludeDay = vacationRepository.findAllIncludeDay(localDate);

        return allIncludeDay;
    }

    private List<VacationResponse> prepareDailyVacation(LocalDate localDate) {
        Vacations vacations = Vacations.of(findDailyVacation(localDate));

        Vacations useDaily = vacations.getUseDaily(localDate);
        Vacations useDailyHour = vacations.getUseDailyHour(localDate);

        List<VacationResponse> collect = Stream
            .concat(useDaily.getVacations().stream(), useDailyHour.getVacations().stream())
            .map(VacationResponse::new)
            .sorted(Comparator.comparing(VacationResponse::getStartDate)
                .thenComparing(VacationResponse::getEndDate))
            .collect(Collectors.toList());

        return collect;
    }

    public List<VacationResponse> prepareDailyVacation(int year, int month, int day) {
        List<VacationResponse> vacationResponses = prepareDailyVacation(LocalDate.of(year, month, day));
        List<String> userIds = vacationResponses.stream()
            .map(VacationResponse::getUserId)
            .collect(Collectors.toList());
        List<User> users = userService.findUserByIds(userIds);

        for (VacationResponse vacationResponse : vacationResponses) {
            for (User user : users) {
                if (vacationResponse.getUserId().equals(user.getId())) {
                    vacationResponse.setUserName(user.getName());
                }
            }
        }

        return vacationResponses;
    }

}
