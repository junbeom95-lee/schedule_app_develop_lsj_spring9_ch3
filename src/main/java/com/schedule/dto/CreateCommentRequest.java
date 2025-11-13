package com.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "유저 고유 아이디를 적어주세요 이것은 추후 세션에서 가져올 예정입니다")
    private Long userId;        //유저 고유 ID
    @NotBlank(message = "댓글 내용을 적어주세요")
    private String content;     //댓글 내용
}
