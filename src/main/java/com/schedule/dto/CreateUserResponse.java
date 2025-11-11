package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateUserResponse {

    private final Long id;
    private final String email;
    private final String username;

    public CreateUserResponse(Long id,  String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
