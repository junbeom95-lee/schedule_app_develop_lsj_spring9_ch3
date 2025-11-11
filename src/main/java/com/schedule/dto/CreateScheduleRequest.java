package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private Long userId;        //유저 고유 ID
    private String title;       //일정 제목
    private String content;     //일정 내용

}
