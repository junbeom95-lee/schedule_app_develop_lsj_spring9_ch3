package com.schedule.domain.comment.model.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final Long userId;
    private final Long scheduleId;
    private final String content;
    private final LocalDateTime createdAt;

    public CreateCommentResponse(Long id, Long userId, Long scheduleId, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.content = content;
        this.createdAt = createdAt;
    }
}
