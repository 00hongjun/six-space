package com.sixshop.sixspace.vacation.repository;

import static com.sixshop.sixspace.vacation.domain.QVacation.vacation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sixshop.sixspace.vacation.domain.Vacation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Deprecated
public class DayOfMonthVacationRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * {@link VacationRepository#findAllByBetweenDates}
     */
    @Deprecated
    public List<Vacation> findAllByBetweenDates(LocalDateTime start, final LocalDateTime end) {
        return queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.startDateTime.time.between(start, end)
                .or(vacation.endDateTime.time.between(start, end)))
            .orderBy(vacation.startDateTime.time.asc())
            .fetch();
    }

    /**
     * {@link VacationRepository#findAllByUserId}
     */
    @Deprecated
    public List<Vacation> findAllByUserId(String userId) {
        return queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.userId.eq(userId))
            .orderBy(vacation.startDateTime.time.asc())
            .fetch();
    }

}
