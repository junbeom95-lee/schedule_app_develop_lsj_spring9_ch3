package com.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "이메일을 확인해주세요")
    @Email(message = "이메일형식으로 적어주세요")
    private String email;
    @NotBlank(message = "비밀번호를 확인해주세요")
    private String password;
}
