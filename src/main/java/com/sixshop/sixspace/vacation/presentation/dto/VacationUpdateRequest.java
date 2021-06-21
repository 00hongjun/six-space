package com.sixshop.sixspace.vacation.presentation.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VacationUpdateRequest {

    private String userId;
    private Integer vacationId;
    private LocalDateTime startTime;
    private Integer useHour;
    private String reason;

}
