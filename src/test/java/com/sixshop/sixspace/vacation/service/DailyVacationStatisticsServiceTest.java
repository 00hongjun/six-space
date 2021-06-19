package com.sixshop.sixspace.vacation.service;

import static org.junit.jupiter.api.Assertions.*;

import com.sixshop.sixspace.vacation.domain.Vacation;
import com.sixshop.sixspace.vacation.presentation.dto.VacationResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DailyVacationStatisticsServiceTest {

    @Autowired
    DailyVacationStatisticsService service;


    @DisplayName("ss")
    @Test
    void ssss() {
        // given
        List<VacationResponse>find = service.compileDailyStatistics(2021, 6, 1);

        // when

        System.out.println(find);

        // then
    }

}