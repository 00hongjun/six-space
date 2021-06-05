package com.sixshop.sixspace.user.presentation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    private String id;
    private String password;
    private String name;
    private String nickName;
    private String email;
    private String slackId;

}
