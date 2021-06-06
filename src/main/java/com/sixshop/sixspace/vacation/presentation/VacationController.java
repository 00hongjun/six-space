package com.sixshop.sixspace.vacation.presentation;

import com.sixshop.sixspace.vacation.domain.DayOfMonthVacation;
import com.sixshop.sixspace.vacation.service.DayOfMonthVacationService;
import java.util.List;
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

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<DayOfMonthVacation>> getVacationsOfMonth(@PathVariable int year, @PathVariable int month) {
        final List<DayOfMonthVacation> vacationsOfMonth = dayOfMonthVacationService.getVacationsOfMonth(year, month);
        return ResponseEntity.ok(vacationsOfMonth);
    }
}

