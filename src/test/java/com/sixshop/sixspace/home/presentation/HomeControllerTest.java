package com.sixshop.sixspace.home.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixshop.sixspace.home.presentation.dto.VacationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("[오늘|내일]이란 값이 들어오면 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"오늘", "내일"})
    void validTwoDay(final String twoDay) throws Exception {
        // given
        final VacationRequest vacationRequest = new VacationRequest(twoDay, "13:00", "1");

        // when
        final ResultActions actions = mockMvc.perform(
            post("/users/{user-id}/vacation", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequest))
        ).andDo(print());

        //then
        actions.andExpect(status().isOk())
               .andDo(print());
    }

    @DisplayName("[오늘|내일]이 아닌 잘못된 일을 입력하면 예외를 발생한다.")
    @Test
    void validTwoDay_exception() throws Exception {
        // given
        final VacationRequest vacationRequest = new VacationRequest("글피", "13:00", "1");

        // when
        final ResultActions actions = mockMvc.perform(
            post("/users/{user-id}/vacation", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequest))
        ).andDo(print());

        //then
        actions.andExpect(status().isBadRequest())
               .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
               .andDo(print());
    }

    @DisplayName("유효한 시작 시간이 들어오면 성공한다.")
    @Test
    void validStartTime() throws Exception {
        // given
        final VacationRequest vacationRequest = new VacationRequest("오늘", "13:00", "1");

        // when
        final ResultActions actions = mockMvc.perform(
            post("/users/{user-id}/vacation", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequest))
        ).andDo(print());

        //then
        actions.andExpect(status().isOk())
               .andDo(print());
    }

    @DisplayName("유효하지 않은 시작 시간이 들어오면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"13:60", "24:00"})
    void validStartTime_exception(final String startTime) throws Exception {
        // given
        final VacationRequest vacationRequest = new VacationRequest("오늘", startTime, "1");

        // when
        final ResultActions actions = mockMvc.perform(
            post("/users/{user-id}/vacation", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequest))
        ).andDo(print());

        //then
        actions.andExpect(status().isBadRequest())
               .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
               .andDo(print());
    }

    @DisplayName("유효한 시간이 들어오면 성공한다.")
    @Test
    void validHour() throws Exception {
        // given
        final VacationRequest vacationRequest = new VacationRequest("오늘", "13:00", "1");

        // when
        final ResultActions actions = mockMvc.perform(
            post("/users/{user-id}/vacation", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequest))
        ).andDo(print());

        //then
        actions.andExpect(status().isOk())
               .andDo(print());
    }

    @DisplayName("유효한 시간이 들어오면 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "9", "10"})
    void validHour_exception(final String hour) throws Exception {
        // given
        final VacationRequest vacationRequest = new VacationRequest("오늘", "13:00", hour);

        // when
        final ResultActions actions = mockMvc.perform(
            post("/users/{user-id}/vacation", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vacationRequest))
        ).andDo(print());

        //then
        actions.andExpect(status().isBadRequest())
               .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
               .andDo(print());
    }
}