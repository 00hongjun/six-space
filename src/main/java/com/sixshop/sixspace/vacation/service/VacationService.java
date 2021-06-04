package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.repository.VacationRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationRepository vacationRepository;

    @Transactional(readOnly = true)
    public List<Vacation> findAllByBetweenDates(final int year, final int month) {
        final LocalDateTime startDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        final LocalDateTime endDayOfMonth = startDayOfMonth.with(lastDayOfMonth())
                                                           .withHour(23)
                                                           .withMinute(59);

        return vacationRepository.findAllByBetweenDates(startDayOfMonth, endDayOfMonth);
    }

    public void getVacationsOfMonth(final int year, final int month) {
        final List<Vacation> vacations = findAllByBetweenDates(year, month);
        final List<FilteredVacation> filtered = filterVacationOfMonth(year, month, vacations);
    }

    private List<FilteredVacation> filterVacationOfMonth(final int year, final int month, final List<Vacation> vacations) {
        final LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
        final LocalDate endDayOfMonth = startDayOfMonth.with(lastDayOfMonth());
        final List<FilteredVacation> filtered = new ArrayList<>();

        for (final Vacation vacation : vacations) {
            final LocalDate vacationStartDay = vacation.getStartDateTime().toLocalDate();
            final LocalDate vacationEndDay = vacation.getEndDateTime().toLocalDate();

            LocalDate cursor = vacationStartDay;
            while (!cursor.isAfter(vacationEndDay)) {
                if (!cursor.isBefore(startDayOfMonth) && !cursor.isAfter(endDayOfMonth)) {
                    filtered.add(new FilteredVacation(vacation.getUserId(), cursor.getDayOfMonth()));
                }
                cursor = cursor.plusDays(1);
            }
        }
        return filtered;
    }
}
