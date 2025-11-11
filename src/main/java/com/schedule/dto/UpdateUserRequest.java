package com.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String email;       //바꿀 이메일
    private String username;    //바꿀 유저명
    private String password;    //현재 비밀번호
    private String newPassword; //바꿀 비밀번호
}
