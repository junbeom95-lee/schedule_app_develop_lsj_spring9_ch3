package com.schedule.service;

import com.schedule.dto.*;
import com.schedule.entity.Comment;
import com.schedule.entity.Schedule;
import com.schedule.entity.User;
import com.schedule.repository.CommentRepository;
import com.schedule.repository.ScheduleRepository;
import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 댓글 일정 기준 조회
     * @param scheduleId 일정 고유 ID
     * @return List<GetCommentResponse> (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAll(Long scheduleId) {

        //1. scheduleId로 댓글들 조회
        List<Comment> commentList = commentRepository.findAllBySchedule_Id(scheduleId);

        //2. List<Entity> 를 List<DTO> 로 변환
        List<GetCommentResponse> response = commentList.stream().map(comment ->
            new GetCommentResponse(comment.getId(),
                    comment.getUser().getId(),
                    comment.getSchedule().getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt())).collect(Collectors.toList());

        //3. List<DTO> 반환
        return response;
    }

    //TODO 댓글 수정 update()
    //TODO Param Long commentId
    //TODO Return UpdateCommentResponse id, userId, scheduleId, content, createdAt, modifiedAt)

    //TODO 댓글 삭제 delete()
    //TODO Param Long commentId
    //TODO Return Void
}
