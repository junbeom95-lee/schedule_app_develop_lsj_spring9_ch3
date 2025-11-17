package com.schedule.domain.comment.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "댓글 내용을 적어주세요")
    private String content;     //댓글 내용
}
