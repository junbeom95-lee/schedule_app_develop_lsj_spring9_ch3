package com.schedule.controller;

import com.schedule.dto.CreateCommentRequest;
import com.schedule.dto.CreateCommentResponse;
import com.schedule.service.CommentService;
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
     * @param scheduleId 일정 고유 ID
     * @param request CreateCommentRequest (userId, content)
     * @return CREATED, CreateCommentResponse (id, userId, scheduleId, content, createdAt)
     */
    @PostMapping("/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> create(@PathVariable Long scheduleId, @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(scheduleId, request));
    }

    //TODO 댓글 일정 기준 조회 getAll()
    //TODO Method : GET, URL : "/schedules/{scheduleId}/comments"
    //TODO PathVariable Long scheduleId
    //TODO ResponseEntity<List<GetCommentResponse>> (id, userId, scheduleId, content, createdAt, modifiedAt) OK

    //TODO 댓글 수정 update()
    //TODO Method : PUT,  URL : "/schedules/comments/{commentId}"
    //TODO PathVariable Long commentId
    //TODO ResponseEntity<UpdateCommentResponse> (id, userId, scheduleId, content, createdAt, modifiedAt) OK

    //TODO 댓글 삭제 delete()
    //TODO Method : DELETE,  URL : "/schedules/comments/{commentId}"
    //TODO PathVariable Long commentId
    //TODO ResponseEntity<Void> NO_CONTENT

}
