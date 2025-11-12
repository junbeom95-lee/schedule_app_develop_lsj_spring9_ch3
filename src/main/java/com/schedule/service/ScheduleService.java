package com.schedule.service;

import com.schedule.dto.*;
import com.schedule.entity.Schedule;
import com.schedule.entity.User;
import com.schedule.repository.ScheduleRepository;
import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정 생성
     * @param request CreateScheduleRequest (username, title, content)
     * @return CreateScheduleResponse (id, username, title, content, createdAt, modifiedAt)
     */

    public CreateScheduleResponse create(CreateScheduleRequest request) {

        //1. 유저 고유 아이디로 User Entity 조회
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

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

        return response;
    }

    /**
     * 일정 조회 다건
     * @param userId 유저 고유 ID
     * @return List<GetScheduleResponse> (id, username, title, content, createdAt, modifiedAt)
     */
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAll(Long userId) {

        List<Schedule> list;

        //1. username을 확인
        if (userId == null) {
            //1-a. 없으면 모두 조회
            list = scheduleRepository.findAll();
        } else {
            //1-b. 있으면 username으로 조회
            User user = userRepository.findById(userId).orElseThrow( () -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

            list = scheduleRepository.findAllByUserId(user.getId());
        }

        //2. List<Entity>에서 List<DTO>로 변환 및 반환
        List<GetScheduleResponse> responseList = new ArrayList<>();
        for (Schedule schedule : list) {
            responseList.add(new GetScheduleResponse(schedule.getId(),
                    schedule.getUser().getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()));
        }

        return responseList;
    }

    /**
     * 일정 조회 단건
     * @param scheduleId 일정 고유 ID
     * @return GetOneScheduleResponse (id, username, title, content, createdAt, modifiedAt)
     */
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {

        //1. 일정 찾기
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ServiceException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //2. 찾은 일정을 DTO로 담아 반환
        GetScheduleResponse response = new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
        return response;
    }

    /**
     * 일정 수정
     * @param scheduleId 일정 고유 ID
     * @param request UpdateScheduleRequest 변경할 title, content
     * @return UpdateScheduleResponse (id, username, title, content, createdAt, modifiedAt)
     */
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {

        //1. id로 일정 조회 없으면 예외 처리
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow( () -> new ServiceException(ExceptionCode.NOT_FOUND_SCHEDULE));

        //2. 영속성 컨텍스트를 활용하여 Entity 변경하여 자동으로 DB 반영
        schedule.update(request.getTitle(), request.getContent());

        //3. 변경된 Entity로 응답 DTO 생성 및 반환
        UpdateScheduleResponse response = new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());

        return response;
    }

    /**
     * 일정 삭제
     * @param scheduleId 일정 고유 ID
     */
    public void delete(Long scheduleId) {
        //1. id로 일정이 존재하는지 확인
        boolean existence = scheduleRepository.existsById(scheduleId);

        //2. 일정이 없으면 throw 있으면 삭제
        if (!existence) throw new ServiceException(ExceptionCode.NOT_FOUND_SCHEDULE);
        else scheduleRepository.deleteById(scheduleId);
    }
}
