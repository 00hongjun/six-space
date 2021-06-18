package com.sixshop.sixspace.vacation.presentation.dto;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.vacation.domain.Vacations;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class UserMonthlyStatisticsResponse {

    private String userId;
    private String userName;
    private VacationInfo vacation;


    @Getter
    public static class VacationInfo {

        private Integer total;
        private Integer use;
        private Integer remain;
        private Integer weekTotal;
        private Integer monthTotal;
        private List<VacationResponse> useList;

    }

    public void setVacationInfo(
        User user, Vacations upcomingVacation, int weekUseVacationHour,
        int monthUseVacationHour, int totalUseHour) {

        this.userId = user.getId();
        this.userName = user.getName();

        VacationInfo vacationInfo = new VacationInfo();
        vacationInfo.total = user.getTotalVacationTime();
        vacationInfo.use = totalUseHour;
        vacationInfo.remain = user.getTotalVacationTime() - totalUseHour;
        vacationInfo.weekTotal = weekUseVacationHour;
        vacationInfo.monthTotal = monthUseVacationHour;
        vacationInfo.useList = upcomingVacation.getVacations()
            .stream()
            .map(VacationResponse::new)
            .sorted(Comparator.comparing(VacationResponse::getStartDate))
            .collect(Collectors.toList());

        this.vacation = vacationInfo;
    }

}
