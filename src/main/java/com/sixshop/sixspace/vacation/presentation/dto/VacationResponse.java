package com.sixshop.sixspace.vacation.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.VacationType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
public class VacationResponse {

    private Integer vacationId;
    private String userId;
    @JsonInclude(Include.NON_EMPTY)
    @Setter
    private String userName;
    private VacationType type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer hour;
    private String reason;

    public VacationResponse(Vacation vacation) {
        this.vacationId = vacation.getId();
        this.userId = vacation.getUserId();
        this.startDate = vacation.getStartDateTimeValue();
        this.endDate = vacation.getEndDateTimeValue();
        this.hour = vacation.getUseHour();
        this.reason = vacation.getReason();
    }

}
