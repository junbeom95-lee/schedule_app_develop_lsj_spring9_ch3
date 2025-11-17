package com.schedule.domain.user.model.dto;

import lombok.Getter;

@Getter
public class SessionUser {      //세션에 저장할 DTO

    private final Long id;
    private final String email;

    public SessionUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
