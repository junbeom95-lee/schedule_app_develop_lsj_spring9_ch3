package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param scheduleId 일정 고유 ID
     * @param request CreateCommentRequest (userId, content)
     * @return CREATED, CreateCommentResponse (id, userId, scheduleId, content, createdAt)
     */
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(@PathVariable Long scheduleId, @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(scheduleId, request));
    }

    /**
     * 댓글 일정 기준 조회
     * @param scheduleId 일정 고유 ID
     * @return OK, List<GetCommentResponse> (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    @GetMapping("/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> getAll(@PathVariable Long scheduleId) {

        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAll(scheduleId));
    }

    /**
     * 댓글 수정
     * @param commentId 댓글 고유 ID
     * @param request 변경할 내용
     * @return OK, UpdateCommentResponse (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> update(@PathVariable Long commentId, @RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(commentId, request));
    }

    //TODO 댓글 삭제 delete()
    //TODO Method : DELETE,  URL : "/schedules/comments/{commentId}"
    //TODO PathVariable Long commentId
    //TODO ResponseEntity<Void> NO_CONTENT

}
