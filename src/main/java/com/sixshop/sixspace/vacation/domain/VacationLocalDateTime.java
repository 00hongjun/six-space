package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VacationLocalDateTime {

    private static final int STANDARD_OF_MINUTE = 10;
    private static final int DAY_VACATION_HOUR = 8;
    private static final LocalTime GO_OFFICE_TIME = LocalTime.of(9, 0);
    private static final LocalTime LEAVE_OFFICE_TIME = LocalTime.of(18, 0);

    @Getter
    private LocalDateTime time;

    private VacationLocalDateTime(LocalDateTime time) {
        this.time = time;
    }

    public static VacationLocalDateTime of(LocalDate date, LocalTime time) {
        if ((time.getMinute() % STANDARD_OF_MINUTE) != 0) {
            throw new IllegalArgumentException("10분 단위로 설정 가능");
        }

        time = time.withSecond(0);
        return new VacationLocalDateTime(LocalDateTime.of(date, time));
    }

    public static VacationLocalDateTime of(LocalDateTime localDateTime) {
        return VacationLocalDateTime.of(localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }

    public VacationLocalDateTime plusHours(int hours) {
        validateOperandHour(hours);

        if (hours < DAY_VACATION_HOUR) {
            return VacationLocalDateTime.of(time.plusHours(hours));
        }

        return useDayVacation(hours);
    }

    private void validateOperandHour(int hours) {
        if (hours < DAY_VACATION_HOUR) {
            return;
        }

        if (hours % DAY_VACATION_HOUR != 0 || !GO_OFFICE_TIME.equals(time.toLocalTime())) {
            throw new IllegalArgumentException("연차 시간 설정 에러");
        }
    }

    private VacationLocalDateTime useDayVacation(int hours) {
        LocalDate localDate = time.plusDays((hours / DAY_VACATION_HOUR) - 1)
            .toLocalDate();

        return VacationLocalDateTime.of(localDate, LEAVE_OFFICE_TIME);
    }

    public Boolean isStartGoOfficeTime() {
        return GO_OFFICE_TIME.equals(time.toLocalTime());
    }

    public Boolean isEndLeaveOfficeTime() {
        return LEAVE_OFFICE_TIME.equals(time.toLocalTime());
    }

}
