package com.sixshop.sixspace.user.domain;

import com.sixshop.sixspace.user.presentation.dto.UserCreateRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String id;
    private String password;
    private String name;
    private String nickName;
    private String email;
    private String slackId;
    private LocalDate joinDate;
    private LocalDate resignationDate;
    private Integer totalVacationTime;

    @OneToMany(mappedBy = "user")
    private List<Role> roles = new ArrayList<>();

    public static User of(UserCreateRequest request) {
        User result = new User();
        result.id = request.getId();
        result.password = request.getPassword();
        result.name = request.getName();
        result.nickName = request.getNickName();
        result.email = request.getEmail();
        result.slackId = request.getSlackId();
        result.joinDate = LocalDate.now();
        result.resignationDate = null;
        result.totalVacationTime = 100;

        return result;
    }
}
