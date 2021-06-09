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
public class DayOfMonthVacationRepository {

    private final JPAQueryFactory queryFactory;

//    @Query(value = "SELECT v FROM Vacation v WHERE v.startDateTime BETWEEN :start AND :end "
//        + "OR v.endDateTime BETWEEN :start AND :end "
//        + "ORDER BY v.startDateTime")
//    List<Vacation> findAllByBetweenDates(@Param("start") final LocalDateTime start, @Param("end") final LocalDateTime end);

    public List<Vacation> findAllByBetweenDates(LocalDateTime start, final LocalDateTime end) {
        return queryFactory
            .select(vacation)
            .from(vacation)
            .where(vacation.startDateTime.between(start, end)
                                         .or(vacation.endDateTime.between(start, end)))
            .orderBy(vacation.startDateTime.asc())
            .fetch();
    }

    public Vacations findAllByBetweenDates2(LocalDateTime start, final LocalDateTime end) {
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
