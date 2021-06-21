package com.sixshop.sixspace.vacation.presentation.dto;

import com.sixshop.sixspace.vacation.domain.Vacation;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VacationResponse {

    private String id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer hour;

    public VacationResponse(Vacation vacation) {
        this.id = vacation.getUserId();
        this.startDate = vacation.getStartDateTimeValue();
        this.endDate = vacation.getEndDateTimeValue();
        this.hour = vacation.getUseHour();
    }
}
