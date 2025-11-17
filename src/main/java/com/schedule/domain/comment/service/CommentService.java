package com.schedule.domain.comment.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.schedule.common.enums.ExceptionCode;
import com.schedule.common.exception.CustomException;
import com.schedule.common.model.CommonResponse;
import com.schedule.domain.comment.model.dto.CreateCommentResponse;
import com.schedule.domain.comment.model.dto.GetCommentResponse;
import com.schedule.domain.comment.model.dto.UpdateCommentResponse;
import com.schedule.domain.comment.model.request.CreateCommentRequest;
import com.schedule.domain.comment.model.request.UpdateCommentRequest;
import com.schedule.common.entity.Comment;
import com.schedule.common.entity.Schedule;
import com.schedule.common.entity.User;
import com.schedule.domain.comment.repository.CommentRepository;
import com.schedule.domain.schedule.repository.ScheduleRepository;
import com.schedule.domain.user.model.dto.SessionUser;
import com.schedule.domain.user.repository.UserRepository;
import com.schedule.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;

    /**
     * 댓글 생성
     * @param sessionUser 세션(Id, email)
     * @param scheduleId 일정 고유 ID
     * @param request CreateCommentRequest (댓글 내용)
     * @return (id, userId, scheduleId, content, createdAt)
     */
    public CommonResponse<?> create(SessionUser sessionUser, Long scheduleId, CreateCommentRequest request) {

        //1. UserId로 유저 조회
        User savedUser = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        //2. scheduleId로 일정 조회
        Schedule savedSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //3. Entity 객체 생성
        Comment comment = new Comment(savedUser, savedSchedule, request.getContent());

        //4. Entity를 DB에 저장하고 영속화된 Entity
        Comment saveComment = commentRepository.save(comment);

        //5. Entity -> DTO 변환 및
        CreateCommentResponse response = new CreateCommentResponse(saveComment.getId(), saveComment.getUser().getId(),
                saveComment.getSchedule().getId(),
                saveComment.getContent(),
                saveComment.getCreatedAt());

        return new CommonResponse<>(HttpStatus.CREATED, response);
    }

    /**
     * 댓글 조회 (일정 기준)
     * @param scheduleId 일정 고유 ID
     * @return List (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    @Transactional(readOnly = true)
    public CommonResponse<?> getAll(Long scheduleId) {

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
        return new CommonResponse<>(HttpStatus.OK, response);
    }

    /**
     * 댓글 수정
     * @param commentId 댓글 고유 ID
     * @param request UpdateCommentRequest (변경할 내용)
     * @return UpdateCommentResponse (id, userId, scheduleId, content, createdAt, modifiedAt)
     */
    public CommonResponse<?> update(SessionUser sessionUser, Long commentId, UpdateCommentRequest request) {

        //1. commentId로 댓글 조회
        Comment savedComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT));

        //2. 세션 id와 comment의 유저 id와 일치하는지 비교
        userService.sessionIdMatches(sessionUser, savedComment.getUser().getId());

        //3. 영속성 컨텍스트 Entity 변경하여 DB 자동 반영
        savedComment.update(request.getContent());

        //4. DTO로 변환 및 반환
        UpdateCommentResponse response = new UpdateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getId(),
                savedComment.getSchedule().getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt());

        return new CommonResponse<>(HttpStatus.OK, response);
    }

    /**
     * 댓글 삭제
     * @param sessionUser 세션(id, email)
     * @param commentId 댓글 고유 ID
     * @return NO_CONTENT, null
     */
    public CommonResponse<?> delete(SessionUser sessionUser, Long commentId) {

        //1. commentId로 조회하여 있는지 여부 확인
        Comment savedComment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT));

        //2. 세션 id와 comment의 유저 id와 일치하는지 비교
        userService.sessionIdMatches(sessionUser, savedComment.getUser().getId());

        //3. 있으면 삭제
        commentRepository.deleteById(commentId);

        return new CommonResponse<>(HttpStatus.NO_CONTENT, null);
    }
}
