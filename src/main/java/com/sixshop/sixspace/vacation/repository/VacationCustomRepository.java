package com.sixshop.sixspace.vacation.repository;

import com.sixshop.sixspace.vacation.domain.Vacation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationCustomRepository {

    List<Vacation> findAllByBetweenDates(LocalDateTime start, final LocalDateTime end);

    List<Vacation> findAllByUserId(String userId);

    public List<Vacation> findAllIncludeDay(LocalDate localDate);

}
