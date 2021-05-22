package com.sixshop.sixspace.vacation.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VacationType {

    BASIC("일반 휴가"),
    SICK_LEAVE("병가"),
    MATERNITY_LEAVE("출산 휴가"),
    COMPENSATION_LEAVE("보상 휴가");

    private String description;

}
