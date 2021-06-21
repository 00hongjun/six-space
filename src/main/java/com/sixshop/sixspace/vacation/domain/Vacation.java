package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userId;
    @Enumerated(EnumType.STRING)
    private VacationType type;
    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "startDateTime"))
    private VacationLocalDateTime startDateTime;
    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "endDateTime"))
    private VacationLocalDateTime endDateTime;
    private Integer useHour;
    private String reason;

    public Vacation(final String userId, final VacationLocalDateTime startDateTime, final Integer useHour) {
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plusHours(useHour);
        this.useHour = useHour;
    }

    @Deprecated
    public Vacation(final String userId, final VacationLocalDateTime startDateTime, final VacationLocalDateTime endDateTime, final Integer useHour) {
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.useHour = useHour;
    }

    public LocalDateTime getStartDateTimeValue() {
        return startDateTime.getTime();
    }

    public LocalDateTime getEndDateTimeValue() {
        return endDateTime.getTime();
    }

    public void updateStartDateTime(VacationLocalDateTime dateTime, int useHour) {
        this.startDateTime = dateTime;
        this.useHour = useHour;
        this.endDateTime = dateTime.plusHours(useHour);
    }

    public void updateReason(String reason) {
        this.reason = reason;
    }
    public boolean isDailyVacation() {
        return startDateTime.isStartGoOfficeTime() && endDateTime.isEndLeaveOfficeTime();
    }

    public boolean isIncludeDatePeriod(LocalDate localDate) {
        LocalDate start = getStartDateTimeValue()
            .toLocalDate();
        LocalDate end = getEndDateTimeValue()
            .toLocalDate();

        return (start.isBefore(localDate) || start.equals(localDate))
            && (end.isAfter(localDate) || end.isEqual(localDate));
    }

}
