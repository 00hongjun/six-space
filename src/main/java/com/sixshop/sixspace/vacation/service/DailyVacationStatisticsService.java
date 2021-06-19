package com.sixshop.sixspace.vacation.service;

import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.Vacations;
import com.sixshop.sixspace.vacation.presentation.dto.VacationResponse;
import com.sixshop.sixspace.vacation.repository.DayOfMonthVacationRepository;
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

    private final DayOfMonthVacationRepository vacationRepository;

    public List<Vacation> findDailyVacation(LocalDate localDate) {
        List<Vacation> allIncludeDay = vacationRepository.findAllIncludeDay(localDate);

        return allIncludeDay;
    }

    public List<VacationResponse> compileDailyStatistics(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
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

}
