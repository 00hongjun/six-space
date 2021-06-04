package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDateTime;
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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer useHour;
    private String reason;

}
