package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String email;       //이메일
    private String username;    //유저명
    private String password;    //비밀번호

}
