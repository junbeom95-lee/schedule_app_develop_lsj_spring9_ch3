package com.schedule.service;

import com.schedule.dto.CreateCommentRequest;
import com.schedule.dto.CreateCommentResponse;
import com.schedule.dto.ExceptionCode;
import com.schedule.dto.ServiceException;
import com.schedule.entity.Comment;
import com.schedule.entity.Schedule;
import com.schedule.entity.User;
import com.schedule.repository.CommentRepository;
import com.schedule.repository.ScheduleRepository;
import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 댓글 생성
     * @param scheduleId 일정 고유 ID
     * @param request CreateCommentRequest (유저 고유 ID, 댓글 내용)
     * @return CreateCommentResponse (id, userId, scheduleId, content, createdAt)
     */
    public CreateCommentResponse create(Long scheduleId, CreateCommentRequest request) {

        //1. UserId로 유저 조회
        User savedUser = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

        //2. scheduleId로 일정 조회
        Schedule savedSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ServiceException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //3. Entity 객체 생성
        Comment comment = new Comment(savedUser, savedSchedule, request.getContent());

        //4. Entity를 DB에 저장하고 영속화된 Entity
        Comment saveComment = commentRepository.save(comment);

        //5. Entity -> DTO 변환 및 반환
        return new CreateCommentResponse(saveComment.getId(), saveComment.getUser().getId(),
                saveComment.getSchedule().getId(),
                saveComment.getContent(),
                saveComment.getCreatedAt());
    }

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
