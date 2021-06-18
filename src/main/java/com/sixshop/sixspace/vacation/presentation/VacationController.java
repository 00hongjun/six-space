package com.sixshop.sixspace.vacation.presentation;

import com.sixshop.sixspace.vacation.presentation.dto.DayOfMonthVacationResponse;
import com.sixshop.sixspace.vacation.service.DayOfMonthVacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final DayOfMonthVacationService dayOfMonthVacationService;
    private final VacationService vacationService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<DayOfMonthVacationResponse> getVacationsOfMonth(@PathVariable int year, @PathVariable int month) {
        final DayOfMonthVacationResponse vacationsOfMonth = dayOfMonthVacationService.getVacationsOfMonth(year, month);
        return ResponseEntity.ok(vacationsOfMonth);
    }

    @GetMapping("/users/{userId}/{year}/{month}")
    public ResponseEntity<ApiResponse> getUserMonthlyStatistics(
        @PathVariable String userId,
        @PathVariable int year,
        @PathVariable int month) {
        UserMonthlyStatisticsResponse response = vacationService
            .generateUserMonthlyStatistics(userId);
        ApiResponse<UserMonthlyStatisticsResponse> result = new ApiResponse<>(
            response);
        return ResponseEntity.ok(result);
    }

}
