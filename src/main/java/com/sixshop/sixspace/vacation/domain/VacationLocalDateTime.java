package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class VacationLocalDateTime {

    private static final int ONE_HOUR = 1;
    private static final LocalTime GO_OFFICE_TIME = LocalTime.of(9, 0);
    private static final LocalTime LEAVE_OFFICE_TIME = LocalTime.of(18, 0);

    @Getter
    private final LocalDateTime time;

    private VacationLocalDateTime(LocalDateTime time) {
        this.time = time;
    }

    public static VacationLocalDateTime of(LocalDate date, LocalTime time) {
        if ((time.getMinute() % 10) != 0) {
            throw new IllegalArgumentException("10분 단위로 설정 가능");
        }

        time = time.withSecond(0);
        return new VacationLocalDateTime(LocalDateTime.of(date, time));
    }

    public static VacationLocalDateTime of(LocalDateTime localDateTime) {
        return VacationLocalDateTime.of(localDateTime.toLocalDate(), localDateTime.toLocalTime());
    }

    public VacationLocalDateTime plusHours(long hours) {
        int remainingHour = getRemainingHour(hours);

        if (remainingHour >= ONE_HOUR) {
            return overTomorrow(remainingHour);
        }
        return inToday(hours);
    }

    private VacationLocalDateTime overTomorrow(long hours) {
        LocalDate localDate = time.plusDays(1)
            .toLocalDate();
        LocalTime localTime = GO_OFFICE_TIME.plusHours(hours);

        return VacationLocalDateTime.of(localDate, localTime);
    }

    private VacationLocalDateTime inToday(long hours) {
        LocalDateTime localDateTime = time.plusHours(hours);
        if (localDateTime.toLocalTime().isAfter(LEAVE_OFFICE_TIME)) {
            localDateTime = localDateTime.minusMinutes(time.getMinute());
        }

        return new VacationLocalDateTime(localDateTime);
    }

    private int getRemainingHour(long hour) {
        int plusHour = time.plusHours(hour).getHour(); // 사용 휴가 시간
        int leaveHour = LEAVE_OFFICE_TIME.getHour(); // 퇴근시간

        if (plusHour <= leaveHour) {
            return 0;
        }

        if (plusHour - leaveHour < ONE_HOUR) {
            return 0;
        }

        return plusHour - leaveHour;
    }

}
