package com.sixshop.sixspace.home.presentation.dto;

import com.sixshop.sixspace.home.presentation.validation.annotation.Hour;
import com.sixshop.sixspace.home.presentation.validation.annotation.StartTime;
import com.sixshop.sixspace.home.presentation.validation.annotation.TwoDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequest {

    @TwoDay
    private String twoDay;

    @StartTime
    private String startTime;

    @Hour
    private String hour;
}
