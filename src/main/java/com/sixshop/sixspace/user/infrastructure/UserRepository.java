package com.sixshop.sixspace.user.infrastructure;

import com.sixshop.sixspace.user.domain.User;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Override
    Optional<User> findById(String userId);


    @Query(value = "SELECT u FROM User u WHERE u.id IN :ids")
    List<User> findUserByIdIn(@Param("ids") final List<String> ids);
}
