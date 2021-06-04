package com.sixshop.sixspace.vacation.presentation;

import com.sixshop.sixspace.vacation.service.VacationService;
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

    private final VacationService vacationService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<String> getVacationsOfMonth(@PathVariable int year, @PathVariable int month) {
        vacationService.getVacationsOfMonth(year, month);
        return ResponseEntity.ok("ok");
    }
}

