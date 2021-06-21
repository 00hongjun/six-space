package com.sixshop.sixspace.user.application;

import com.sixshop.sixspace.exception.ErrorCode;
import com.sixshop.sixspace.exception.ServiceException;
import com.sixshop.sixspace.user.domain.User;
import com.sixshop.sixspace.user.infrastructure.UserRepository;
import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
            .orElseThrow(() -> new ServiceException(ErrorCode.NOT_FOUND_USER));

        List<String> roleNames = user.getRoleNames();

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getId())
            .password(user.getPassword())
            .roles(roleNames.get(0))
            .build();
    }

}
