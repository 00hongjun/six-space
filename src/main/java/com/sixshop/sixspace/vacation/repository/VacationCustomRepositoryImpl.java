package com.sixshop.sixspace.vacation.repository;

import static com.sixshop.sixspace.vacation.domain.QVacation.vacation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixshop.sixspace.vacation.domain.Vacation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.Builder.ObtainVia;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VacationCustomRepositoryImpl implements VacationCustomRepository{

    private final JPAQueryFactory queryFactory;

    public List<Vacation> findAllByBetweenDates(LocalDateTime start, final LocalDateTime end) {
        return queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.startDateTime.time.between(start, end)
                .or(vacation.endDateTime.time.between(start, end)))
            .orderBy(vacation.startDateTime.time.asc())
            .fetch();
    }

    public List<Vacation> findAllByUserId(String userId) {
        return queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.userId.eq(userId))
            .orderBy(vacation.startDateTime.time.asc())
            .fetch();
    }

    public List<Vacation> findAllIncludeDay(LocalDate localDate) {
        return queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.startDateTime.time.after(LocalDateTime.of(localDate, LocalTime.MIN))
                .and(vacation.endDateTime.time.before(LocalDateTime.of(localDate, LocalTime.MAX))))
            .orderBy(vacation.startDateTime.time.asc())
            .fetch();
    }

}
