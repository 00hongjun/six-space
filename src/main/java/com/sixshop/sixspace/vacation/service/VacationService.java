package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.Vacations;
import com.sixshop.sixspace.vacation.repository.VacationRepository;
import com.sixshop.sixspace.vacation.repository.VacationRepository2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationService {

    private static final int WEEK_DAYS = 7;
    private static final int LUNCH_HOUR = 13;
    private static final int FINISH_WORK_HOUR = 18;
    private static final LocalTime TODAY_LAST_TIME = LocalTime.of(23, 59);
    private final VacationRepository vacationRepository;
    private final VacationRepository2 vacationRepository2;

    @Transactional(readOnly = true)
    public List<Vacation> findAllByBetweenDates(final int year, final int month) {
        final LocalDateTime startDayOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        final LocalDateTime endDayOfMonth = startDayOfMonth.with(lastDayOfMonth())
            .withHour(23)
            .withMinute(59);

//        return vacationRepository.findAllByBetweenDates(startDayOfMonth, endDayOfMonth);
        return vacationRepository2.findAllByBetweenDates(startDayOfMonth, endDayOfMonth)
            .getVacations();
    }

    public void getVacationsOfMonth(final int year, final int month) {
        final List<Vacation> vacations = findAllByBetweenDates(year, month);
        final List<FilteredVacation> filtered = filterVacationOfMonth(year, month, vacations);
    }

    /**
     * TODO
     * 1. userName select
     * 2. 중복 제거
     * 3. 중복 제거하면서 같은날이면 시간 합쳐서 연차인지 시간휴가인지 계산
     * 4. 테스트 코드 작성
     */
    private List<FilteredVacation> filterVacationOfMonth(final int year, final int month,
        final List<Vacation> vacations) {
        final LocalDate startDayOfMonth = LocalDate.of(year, month, 1);
        final LocalDate endDayOfMonth = startDayOfMonth.with(lastDayOfMonth());
        final List<FilteredVacation> filtered = new ArrayList<>();

        for (final Vacation vacation : vacations) {
            final LocalDate from = vacation.getStartDateTime().getTime().toLocalDate();
            final LocalDate to = vacation.getEndDateTime().getTime().toLocalDate();

            int totalUseHour = vacation.getUseHour();
            int startHour = vacation.getStartDateTime().getTime().getHour();

            LocalDate cursor = from;

            while (!cursor.isAfter(to)) {
                int use = getUseHour(startHour, totalUseHour);
                if (!cursor.isBefore(startDayOfMonth) && !cursor.isAfter(endDayOfMonth)) {
                    filtered.add(new FilteredVacation(vacation.getUserId(), cursor.getDayOfMonth(), use));
                }

                cursor = cursor.plusDays(1);
                totalUseHour -= use;
                startHour = 9;
            }

            if (totalUseHour != 0) {
                log.warn("hour calculate error : {}", vacation);
                throw new RuntimeException("일 별 휴가를 계산할 수 없습니다. 관리자에게 문의하세요");
            }
        }
        return filtered;
    }

    private int getUseHour(final int startHour, final int totalUseVacationHour) {
        if (totalUseVacationHour < 8) {
            return totalUseVacationHour;
        }

        if (startHour < LUNCH_HOUR) {
            return FINISH_WORK_HOUR - startHour - 1;
        }
        return FINISH_WORK_HOUR - startHour;
    }

    public void getAboutMonthDuration(String userId) {
        LocalDate now = LocalDate.now();
        LocalDate lastDay = getAboutMonthStartDate(now);
        LocalDate firstDay = getAboutMonthEndDate(now);

        Vacations vacations = vacationRepository2.findAllByBetweenDates(
            LocalDateTime.of(firstDay, LocalTime.MIDNIGHT),
            LocalDateTime.of(lastDay, TODAY_LAST_TIME));

        int weekUseVacationHour = vacations.bringWeekUseVacationHour(now);
        int monthUseVacationHour = vacations.bringMonthVacationHour(now);
    }

    private LocalDate getAboutMonthStartDate(LocalDate now) {
        LocalDate lastDay = now.with(lastDayOfMonth());

        if (ChronoUnit.DAYS.between(now, lastDay) < WEEK_DAYS) {
            lastDay = now.plusDays(WEEK_DAYS);
        }

        return lastDay;
    }

    private LocalDate getAboutMonthEndDate(LocalDate now) {
        LocalDate firstDay = now.with(firstDayOfMonth());

        if (ChronoUnit.DAYS.between(now, firstDay) < WEEK_DAYS) {
            firstDay = now.minusDays(WEEK_DAYS);
        }

        return firstDay;
    }

}
