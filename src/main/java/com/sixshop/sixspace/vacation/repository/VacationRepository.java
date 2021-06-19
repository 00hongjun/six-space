package com.sixshop.sixspace.vacation.repository;

import com.sixshop.sixspace.vacation.domain.Vacation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface VacationRepository
    extends JpaRepository<Vacation, Integer>, QuerydslPredicateExecutor<Vacation>, VacationCustomRepository {

    Optional<Vacation> findById(int vacationId);

}
