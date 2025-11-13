package com.schedule.service;

import com.schedule.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //TODO 댓글 생성 create()
    //TODO Param Long scheduleId, CreateCommentRequest request(userId, content)
    //TODO Return CreateCommentResponse (id, userId, scheduleId, content, createdAt)

    //TODO 댓글 일정 기준 조회 getAll()
    //TODO Transactional(readOnly = true)
    //TODO Param Long scheduleId
    //TODO Return List<GetCommentResponse> (id, userId, scheduleId, content, createdAt, modifiedAt)

    //TODO 댓글 수정 update()
    //TODO Param Long commentId
    //TODO Return UpdateCommentResponse id, userId, scheduleId, content, createdAt, modifiedAt)

    //TODO 댓글 삭제 delete()
    //TODO Param Long commentId
    //TODO Return Void
}
