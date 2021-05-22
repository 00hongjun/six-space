package com.sixshop.sixspace.vacation.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
