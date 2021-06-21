package com.sixshop.sixspace.user.application;

import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
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

}
