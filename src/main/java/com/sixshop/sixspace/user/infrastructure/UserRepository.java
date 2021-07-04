package com.sixshop.sixspace.user.infrastructure;

import com.sixshop.sixspace.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    @Override
    Optional<User> findById(String userId);

    List<User> findAllByIdIn(final List<String> ids);

    Optional<User> findBySlackId(final String slack);
}
