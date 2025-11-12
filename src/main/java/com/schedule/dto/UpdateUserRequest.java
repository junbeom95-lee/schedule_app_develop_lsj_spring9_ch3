package com.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "이메일을 확인해주세요")
    @Email(message = "이메일형식으로 적어주세요")
    private String email;       //바꿀 이메일
    @NotBlank(message = "이름을 확인해주세요")
    @Size(max = 50, message = "이름은 최대 50글자입니다")
    private String username;    //바꿀 유저명
    @NotBlank(message = "비밀번호를 확인해주세요")
    private String password;    //현재 비밀번호
    @NotBlank(message = "새로운 비밀번호를 확인해주세요")
    private String newPassword; //바꿀 비밀번호
}
