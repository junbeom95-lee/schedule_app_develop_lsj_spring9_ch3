package com.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String email;       //이메일
    private String username;    //유저명
}
