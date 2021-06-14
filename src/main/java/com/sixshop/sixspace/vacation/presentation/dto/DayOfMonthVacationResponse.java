package com.sixshop.sixspace.vacation.presentation.dto;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayOfMonthVacationResponse {

    private Map<Integer, List<DayOfMonthVacation>> date;

    public DayOfMonthVacationResponse(final List<DayOfMonthVacation> vacationsOfMonth) {
        this.date = vacationsOfMonth.stream()
                                    .collect(groupingBy(DayOfMonthVacation::getDay));
    }
}
