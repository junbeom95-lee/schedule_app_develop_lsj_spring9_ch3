package com.schedule.domain.schedule.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    @NotBlank(message = "일정 제목을 적어주세요")
    @Size(max = 100, message = "일정 제목을 길게 작성하였습니다")
    private String title;       //일정 제목
    private String content;     //일정 내용

}
