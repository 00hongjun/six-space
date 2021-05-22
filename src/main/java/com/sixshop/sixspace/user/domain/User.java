package com.sixshop.sixspace.user.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

    @Id
    private String id;
    private String password;
    private String name;
    private String nickName;
    private String email;
    private String slackId;
    private LocalDateTime joinDate;
    private LocalDateTime resignationDate;
    private Integer totalVacationTime;

    @OneToMany(mappedBy = "user")
    private List<Role> roles = new ArrayList<>();

}
