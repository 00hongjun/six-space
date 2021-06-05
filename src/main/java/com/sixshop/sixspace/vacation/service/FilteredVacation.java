package com.sixshop.sixspace.vacation.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteredVacation {
    private String userId;
    // private String userName;
    private int day;
    private int useHour;
    private boolean isFull;

    public FilteredVacation(final String userId, final int day, final int useHour) {
        this.userId = userId;
        this.day = day;
        this.useHour = useHour;
        this.isFull = useHour == 8;
    }
}
