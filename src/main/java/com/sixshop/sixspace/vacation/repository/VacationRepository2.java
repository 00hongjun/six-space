package com.sixshop.sixspace.vacation.repository;

import static com.sixshop.sixspace.vacation.domain.QVacation.vacation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.domain.Vacations;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class VacationRepository2 {

    private final JPAQueryFactory queryFactory;

    public Vacations findAllByBetweenDates(LocalDateTime start, final LocalDateTime end) {
        List<Vacation> fetch = queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.startDateTime.time.between(start, end)
                .or(vacation.endDateTime.time.between(start, end)))
            .orderBy(vacation.startDateTime.time.asc())
            .fetch();

        return Vacations.of(fetch);
    }

}
