package com.schedule.domain.user.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이메일을 확인해주세요")
    private String email;       //이메일
    @NotBlank(message = "이름을 확인해주세요")
    @Size(max = 50, message = "이름은 최대 50글자입니다")
    private String username;    //유저명
    @NotBlank(message = "비밀번호를 확인해주세요")
    private String password;    //비밀번호

}
