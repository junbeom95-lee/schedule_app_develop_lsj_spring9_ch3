package com.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private String username;
    private String title;
    private String content;

}
