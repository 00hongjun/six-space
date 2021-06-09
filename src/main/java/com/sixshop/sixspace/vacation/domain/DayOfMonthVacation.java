package com.sixshop.sixspace.vacation.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOfMonthVacation {
    private String userId;
    private String userName;
    private int day;
    private int useHour;
    private boolean isFull;

    public DayOfMonthVacation(final String userId, final int day, final int useHour) {
        this.userId = userId;
        this.day = day;
        this.useHour = useHour;
        this.isFull = useHour == 8;
    }

    public boolean isMine(final String userId) {
        return this.userId.equals(userId);
    }
}
