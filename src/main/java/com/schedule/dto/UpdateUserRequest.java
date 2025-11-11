package com.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String username;    //유저명
    private String email;       //이메일
}
