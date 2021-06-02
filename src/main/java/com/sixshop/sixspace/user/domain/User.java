package com.sixshop.sixspace.user.domain;

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

    // TODO User 등록 api에서 param 받는걸로 변경
    public static final User of(String id) {
        User result = new User();
        result.id = id;
        result.password = "1234";
        result.name = "홍준";
        result.nickName = "이건 별명";
        result.email = "hjchoi@sixhsop.com";
        result.slackId = "a1b2c3";
        result.joinDate = LocalDate.of(2021, 01, 01);
        result.resignationDate = null;
        result.totalVacationTime = 100;

        return result;
    }

}
