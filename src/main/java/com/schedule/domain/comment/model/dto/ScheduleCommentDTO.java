package com.schedule.domain.comment.model.dto;

import lombok.Getter;

@Getter
public class ScheduleCommentDTO {

    private final Long scheduleId;
    private final Long count;

    public ScheduleCommentDTO(Long scheduleId, Long count) {
        this.scheduleId = scheduleId;
        this.count = count;
    }
}
