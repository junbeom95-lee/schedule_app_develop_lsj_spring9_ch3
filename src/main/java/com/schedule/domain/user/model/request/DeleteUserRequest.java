package com.schedule.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteUserRequest {

    @NotBlank(message = "비밀번호를 확인해주세요")
    private String password;
}
