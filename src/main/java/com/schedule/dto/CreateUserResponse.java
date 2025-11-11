package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateUserResponse {

    private final Long id;
    private final String username;
    private final String email;

    public CreateUserResponse(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
