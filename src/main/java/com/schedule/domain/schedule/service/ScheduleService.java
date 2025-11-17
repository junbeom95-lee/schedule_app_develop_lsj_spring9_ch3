package com.schedule.domain.schedule.service;

import com.schedule.common.enums.ExceptionCode;
import com.schedule.common.exception.CustomException;
import com.schedule.common.model.CommonResponse;
import com.schedule.domain.comment.model.dto.ScheduleCommentDTO;
import com.schedule.domain.schedule.model.dto.CreateScheduleResponse;
import com.schedule.domain.schedule.model.dto.GetSchedulePageResponse;
import com.schedule.domain.schedule.model.dto.GetScheduleResponse;
import com.schedule.domain.schedule.model.dto.UpdateScheduleResponse;
import com.schedule.domain.schedule.model.request.CreateScheduleRequest;
import com.schedule.domain.schedule.model.request.UpdateScheduleRequest;
import com.schedule.common.entity.Schedule;
import com.schedule.common.entity.User;
import com.schedule.domain.comment.repository.CommentRepository;
import com.schedule.domain.schedule.repository.ScheduleRepository;
import com.schedule.domain.user.model.dto.SessionUser;
import com.schedule.domain.user.repository.UserRepository;
import com.schedule.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    /**
     * 일정 생성
     * @param sessionUser 세션(id, email)
     * @param request CreateScheduleRequest (title, content)
     * @return (id, userId, title, content, createdAt, modifiedAt)
     */
    public CommonResponse<?> create(SessionUser sessionUser, CreateScheduleRequest request) {

        //1. 유저 고유 아이디로 User Entity 조회
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        //2. 요청받은 DTO를 Entity 객체로 변환
        Schedule schedule = new Schedule(user, request.getTitle(), request.getContent());

        //3. 변환된 Entity를 DB에 저장하고 영속화된 Entity를 반환받음
        Schedule savedSchedule = scheduleRepository.save(schedule);

        //4. 저장된 Entity를 응답 DTO로 변환하여 Controller에 반환
        CreateScheduleResponse response = new CreateScheduleResponse(savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt());

        return new CommonResponse<>(HttpStatus.CREATED, response);
    }

    /**
     * 일정 페이징 조회
     * @param userId 유저 고유 ID
     * @param pageNumber 페이지 번호
     * @param pageSize 페이지 크기
     * @return PagedModel<GetSchedulePageResponse> 페이징 DTO (id, userId, title, content, commentCount, createdAt, modifiedAt)
     */
    @Transactional(readOnly = true)
    public CommonResponse<?> getAll(Long userId, int pageNumber, int pageSize) {

        //1. 페이지 번호 default는 처음으로, 페이지 크기 default는 10으로 적용
        if(pageNumber < 1) pageNumber = 0;
        if(pageSize == 0) pageSize = 10;

        //2. 페이지 번호와 페이지 크기 및 수정일 기준으로 내림차순 정렬 설정
        Sort sort = Sort.by("modifiedAt").descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Schedule> schedulePage;

        //3. username을 확인
        if (userId == null) {
            //3-a. 없으면 모두 조회
            schedulePage = scheduleRepository.findAll(pageable);
        } else {
            //3-b. 있으면 username으로 조회
            User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));
            schedulePage = scheduleRepository.findAllByUserId(user.getId(), pageable);
        }

        //4. 일정 ID List로 댓글 개수 조회
        List<ScheduleCommentDTO> ScheduleIdCommentCount = commentRepository.countCommentByScheduleIdList();

        //5. Page<Entity>에서 Page<DTO>로 변환 및 반환
        Page<GetSchedulePageResponse> responsePage = schedulePage.map(s -> {
            //5-1. 댓글 개수 확인
            Long count = ScheduleIdCommentCount.stream()
                    .filter(dto -> Objects.equals(dto.getScheduleId(), s.getId()))
                    .map(ScheduleCommentDTO::getCount)
                    .findFirst().orElse(0L);

            //5-2. 응답 DTO로 반환
            return new GetSchedulePageResponse(
                    s.getId(),
                    s.getUser().getId(),
                    s.getTitle(),
                    s.getContent(),
                    count,
                    s.getCreatedAt(),
                    s.getModifiedAt());
        });

        //6. PagedModel로 바꿔서 반환
        return new CommonResponse<>(HttpStatus.OK, new PagedModel<>(responsePage));
    }

    /**
     * 일정 조회 단건
     * @param scheduleId 일정 고유 ID
     * @return GetOneScheduleResponse (id, userId, title, content, createdAt, modifiedAt)
     */
    @Transactional(readOnly = true)
    public CommonResponse<?> getOne(Long scheduleId) {

        //1. 일정 찾기
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //2. 찾은 일정을 DTO로 담아 반환
        GetScheduleResponse response = new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
        return new CommonResponse<>(HttpStatus.OK, response);
    }


    /**
     * 일정 수정
     * @param sessionUser 세선(id, email)
     * @param scheduleId 일정 고유 ID
     * @param request (title, content)
     * @return (id, userId, title, content, createdAt, modifiedAt)
     */
    public CommonResponse<?> update(SessionUser sessionUser, Long scheduleId, UpdateScheduleRequest request) {

        //1. id로 일정 조회 없으면 예외 처리
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow( () -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //2. 세션 id와 일정을 작성한 유저의 id를 확인
        userService.sessionIdMatches(sessionUser, schedule.getUser().getId());

        //3. 영속성 컨텍스트를 활용하여 Entity 변경하여 자동으로 DB 반영
        schedule.update(request.getTitle(), request.getContent());

        //4. 변경된 Entity로 응답 DTO 생성 및 반환
        UpdateScheduleResponse response = new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());

        return new CommonResponse<>(HttpStatus.OK, response);
    }


    /**
     * 일정 삭제
     * @param sessionUser 세션(id, email)
     * @param scheduleId 일정 고유 ID
     * @return NO_CONTENT, null
     */
    public CommonResponse<?> delete(SessionUser sessionUser, Long scheduleId) {

        //1. id로 일정 조회 없으면 예외 처리
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow( () -> new CustomException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //2. 세션 id와 일정을 작성한 유저의 id를 확인
        userService.sessionIdMatches(sessionUser, schedule.getUser().getId());

        //3. 있으면 삭제
        scheduleRepository.deleteById(scheduleId);

        return new CommonResponse<>(HttpStatus.NO_CONTENT, null);
    }
}
