package com.schedule.domain.comment.controller;

import com.schedule.common.model.CommonResponse;
import com.schedule.domain.comment.model.request.CreateCommentRequest;
import com.schedule.domain.comment.model.request.UpdateCommentRequest;
import com.schedule.domain.comment.service.CommentService;
import com.schedule.domain.user.model.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param sessionUser 세션(id, email)
     * @param scheduleId 일정 고유 ID
     * @param request CreateCommentRequest(content)
     * @return (id, userId, scheduleId, content, createdAt)
     */
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CommonResponse<?>> create(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long scheduleId, @RequestBody CreateCommentRequest request) {

        CommonResponse<?> result = commentService.create(sessionUser, scheduleId, request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 댓글 일정 기준 조회
     * @param scheduleId 일정 고유 ID
     * @return OK, (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<CommonResponse<?>> getAll(@PathVariable Long scheduleId) {

        CommonResponse<?> result = commentService.getAll(scheduleId);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 댓글 수정
     * @param sessionUser 세선(id, email)
     * @param commentId 댓글 고유 ID
     * @param request 변경할 내용
     * @return OK, (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse<?>> update(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {

        CommonResponse<?> result = commentService.update(sessionUser, commentId, request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 댓글 삭제
     * @param commentId 댓글 고유 ID
     * @return NO_CONTENT
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommonResponse<?>> delete(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long commentId) {
        CommonResponse<?> response = commentService.delete(sessionUser, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
