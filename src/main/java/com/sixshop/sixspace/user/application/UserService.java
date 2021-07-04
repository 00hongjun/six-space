package com.sixshop.sixspace.user.application;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void createUser(UserCreateRequest request) {
        User user = User.of(request);
        userRepository.save(user);
    }

    public List<User> findUserByIds(List<String> userIds) {
        return userRepository.findAllByIdIn(userIds);
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("회원 조회 실패"));
    }

    public User findUserBySlackId(String slackId) {
        return userRepository.findBySlackId(slackId)
                             .orElseThrow(() -> new IllegalArgumentException("슬랙 ID로 회원 조회 실패"));
    }

}
