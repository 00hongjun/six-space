package com.sixshop.sixspace.vacation.service;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.vacation.domain.Vacations;
import com.sixshop.sixspace.vacation.presentation.dto.UserMonthlyStatisticsResponse;
import com.sixshop.sixspace.vacation.repository.DayOfMonthVacationRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacationService {

    private static final int WEEK_DAYS = 7;
    private static final int LUNCH_HOUR = 13;
    private static final int FINISH_WORK_HOUR = 18;
    private static final LocalTime TODAY_LAST_TIME = LocalTime.of(23, 59);
    private final DayOfMonthVacationRepository vacationQueryRepository;
    private final UserRepository userRepository;

    /**
     * TODO - 다가오는 휴가는 무제한으로 모두 조회 해야 함
     */
    public UserMonthlyStatisticsResponse generateUserMonthlyStatistics(String userId) {
        LocalDate now = LocalDate.now();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("회원 조회 실패"));
        Vacations vacations = Vacations.of(vacationQueryRepository.findAllByUserId(userId));

        int weekUseVacationHour = vacations
            .bringVacationByRange(bringWeekStartDateTime(now), bringWeekEndDateTime(now))
            .bringTotalUseHour();
        int monthUseVacationHour = vacations.bringVacationByRange(
            LocalDateTime.of(now.with(firstDayOfMonth()), LocalTime.MIN),
            LocalDateTime.of(now.with(lastDayOfMonth()), LocalTime.MAX))
            .bringTotalUseHour();
        int totalUseHour = vacations.bringTotalUseHour();

        Vacations upcomingVacation = vacations.bringUpcomingVacation(LocalDateTime.of(now, LocalTime.MIN));

        UserMonthlyStatisticsResponse response = new UserMonthlyStatisticsResponse();
        response.setVacationInfo(user, upcomingVacation, weekUseVacationHour, monthUseVacationHour, totalUseHour);
        return response;
    }

    private LocalDateTime bringWeekStartDateTime(LocalDate criterion) {
        int sundayNumber = 0;
        int criteriaNumber = criterion.getDayOfWeek().getValue();

        if (criterion.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            criteriaNumber = sundayNumber;
        }

        return LocalDateTime.of(  // '주'의 시작
            criterion.minusDays(criteriaNumber - sundayNumber),
            LocalTime.MIN);
    }

    private LocalDateTime bringWeekEndDateTime(LocalDate criterion) {
        int sundayNumber = 0;
        int saturdayNumber = DayOfWeek.SATURDAY.getValue(); // 6
        int criteriaNumber = criterion.getDayOfWeek().getValue();

        if (criterion.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            criteriaNumber = sundayNumber;
        }

        return LocalDateTime.of( // '주'의 종료
            criterion.plusDays(saturdayNumber - criteriaNumber),
            LocalTime.MAX);
    }

}
