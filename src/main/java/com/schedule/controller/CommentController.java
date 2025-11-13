package com.schedule.controller;

import com.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class CommentController {

    private final CommentService commentService;

    //TODO 댓글 생성 create()
    //TODO Method : POST, URL : "/schedules/{scheduleId}/comments"
    //TODO PathVariable Long scheduleId
    //TODO RequestBody CreateCommentRequest (userId, content)
    //TODO ResponseEntity<CreateCommentResponse> (id, userId, scheduleId, content, createdAt) CREATED

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
