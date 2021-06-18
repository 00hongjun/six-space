package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
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

}
