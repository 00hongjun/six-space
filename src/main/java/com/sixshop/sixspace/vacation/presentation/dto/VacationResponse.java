package com.sixshop.sixspace.vacation.presentation.dto;

import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.VacationType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VacationResponse {

    private Integer id;
    private String userId;
    private VacationType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer hour;
    private String reason;

    public VacationResponse(Vacation vacation) {
        this.id = vacation.getId();
        this.userId = vacation.getUserId();
        this.startDate = vacation.getStartDateTimeValue();
        this.endDate = vacation.getEndDateTimeValue();
        this.hour = vacation.getUseHour();
        this.reason = vacation.getReason();
    }

}
