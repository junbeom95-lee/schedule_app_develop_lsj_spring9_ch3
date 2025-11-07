package com.schedule.service;

import com.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //TODO 일정 생성 create()
    //TODO Param CreateScheduleRequest (username, title, content)
    //TODO Return CreateScheduleResponse (id, username, title, content, createdAt, modifiedAt)

    //TODO 일정 조회 다건 getAll() - username 있을 때
    //TODO Param (String username)
    //TODO Return List<GetScheduleResponse> (id, username, title, content, createdAt, modifiedAt)

    //TODO 일정 조회 다건 getAll() - username 없을 때
    //TODO Return List<GetScheduleResponse> (id, username, title, content, createdAt, modifiedAt)

    //TODO 일정 조회 단건 getOne()
    //TODO Method : GET,  URL : "/schedules/{id}"
    //TODO Param (String username)
    //TODO Return GetOneScheduleResponse (id, username, title, content, createdAt, modifiedAt)

    //TODO 일정 수정 update()
    //TODO Method : PUT,  URL : "/schedules/{id}"
    //TODO Param (String username), UpdateScheduleRequest (title, content)
    //TODO Return UpdateScheduleResponse (id, username, title, content, createdAt, modifiedAt)

    //TODO 일정 삭제 delete()
    //TODO Method : DELETE,  URL : "/schedules/{id}"
    //TODO PathVariable (String username)
    //TODO ResponseEntity<Void> NO_CONTENT
}
