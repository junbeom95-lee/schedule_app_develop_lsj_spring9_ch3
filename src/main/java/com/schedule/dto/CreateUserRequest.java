package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String username;    //유저명
    private String email;       //이메일
    //TODO 비밀번호 필드 생성

    public CreateUserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
