package com.sixshop.sixspace.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
class UserRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    UserRepository userRepository;

    @DisplayName("유저 저장")
    @Test
    void save() {
        // given
        UserCreateRequest dto = new UserCreateRequest();
        dto.setId("hongjun");
        dto.setPassword("123");

        User user = User.of(dto);
        em.persist(user);

        // when
        userRepository.save(user);
        User target = userRepository.findById("hongjun")
            .get();

        // then
        assertThat(user).isEqualTo(target);
    }

    @DisplayName("슬랙 ID로 유저를 조회할 수 있다.")
    @Test
    void findBySlackId() {
        // given
        final String slackId = "U01SXH2CRT7";

        // when
        User actual = userRepository.findBySlackId(slackId)
                                    .get();

        // then
        assertThat(actual.getSlackId()).isEqualTo(slackId);
    }

    @DisplayName("없는 슬랙 ID로 유저를 조회할 경우 예외 발생을 성공한다.")
    @Test
    void findBySlackId_exception() {
        // given
        final String slackId = "asdf";

        // when then
        assertThatThrownBy(() -> userRepository.findBySlackId(slackId).get());
    }

}