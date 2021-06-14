package com.sixshop.sixspace.vacation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOfMonthVacation {

    private String userId;
    private String userName;
    @JsonIgnore
    private int day;
    private int useHour;

    public DayOfMonthVacation(final String userId, final int day, final int useHour) {
        this.userId = userId;
        this.day = day;
        this.useHour = useHour;
    }

    public boolean isMine(final String userId) {
        return this.userId.equals(userId);
    }
}
