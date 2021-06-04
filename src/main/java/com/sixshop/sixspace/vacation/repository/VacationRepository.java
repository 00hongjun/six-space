package com.sixshop.sixspace.vacation.repository;

import com.sixshop.sixspace.vacation.domain.Vacation;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VacationRepository extends JpaRepository<Vacation, Integer> {

    // TODO : USER 테이블과 INNER JOIN 해서 USER_NAME을 가져와야함
    @Query(value = "SELECT v FROM Vacation v WHERE v.startDateTime BETWEEN :start AND :end "
                                             + "OR v.endDateTime BETWEEN :start AND :end "
                                             + "ORDER BY v.startDateTime")
    List<Vacation> findAllByBetweenDates(@Param("start") final LocalDateTime start, @Param("end") final LocalDateTime end);
}
