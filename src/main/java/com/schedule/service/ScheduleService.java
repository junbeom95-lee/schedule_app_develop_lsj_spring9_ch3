package com.schedule.service;

import com.schedule.dto.CreateScheduleRequest;
import com.schedule.dto.CreateScheduleResponse;
import com.schedule.dto.GetScheduleResponse;
import com.schedule.entity.Schedule;
import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 생성
     * @param request CreateScheduleRequest (username, title, content)
     * @return CreateScheduleResponse (id, username, title, content, createdAt, modifiedAt)
     */
    public CreateScheduleResponse create(CreateScheduleRequest request) {

        //1. 요청받은 DTO를 Entity 객체로 변환
        Schedule schedule = new Schedule(request.getUsername(), request.getTitle(), request.getContent());

        //2. 변환된 Entity를 DB에 저장하고 영속화된 Entity를 반환받음
        Schedule savedSchedule = scheduleRepository.save(schedule);

        //3. 저장된 Entity를 응답 DTO로 변환하여 Controller에 반환
        CreateScheduleResponse response = new CreateScheduleResponse(savedSchedule.getId(),
                savedSchedule.getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt());

        return response;
    }

    /**
     * 일정 조회 다건
     * @param username 작성 유저명
     * @return List<GetScheduleResponse> (id, username, title, content, createdAt, modifiedAt)
     */
    public List<GetScheduleResponse> getAll(String username) {

        List<Schedule> list;

        //1. username을 확인
        if (username.isBlank()) {
            //1-a. 없으면 모두 조회
            list = scheduleRepository.findAll();
        } else {
            //1-b. 있으면 username으로 조회
            list = scheduleRepository.findAllByUsername(username);
        }

        //2. List<Entity>에서 List<DTO>로 변환 및 반환
        List<GetScheduleResponse> responseList = new ArrayList<>();
        for (Schedule schedule : list) {
            responseList.add(new GetScheduleResponse(schedule.getId(),
                    schedule.getUsername(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()));
        }

        return responseList;
    }

    /**
     * 일정 조회 단건
     * @param id 일정 고유 ID
     * @return GetOneScheduleResponse (id, username, title, content, createdAt, modifiedAt)
     */
    public GetScheduleResponse getOne(Long id) {

        //1. 일정 찾기
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("찾으시는 일정이 없습니다."));

        //2. 찾은 일정을 DTO로 담아 반환
        GetScheduleResponse response = new GetScheduleResponse(
                schedule.getId(),
                schedule.getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt());
        return response;
    }

    //TODO 일정 수정 update()
    //TODO Param (Long id), UpdateScheduleRequest (title, content)
    //TODO Return UpdateScheduleResponse (id, username, title, content, createdAt, modifiedAt)

    //TODO 일정 삭제 delete()
    //TODO Param (Long id)
    //TODO ResponseEntity<Void> NO_CONTENT
}
