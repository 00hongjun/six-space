package com.sixshop.sixspace.vacation.presentation.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class VacationCreateRequest {

    private String userId;
    private LocalDateTime startTime;
    private Integer useHour;
    private String reason;

}
