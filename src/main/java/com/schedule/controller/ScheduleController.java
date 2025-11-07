package com.schedule.controller;

import com.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    //TODO 일정 생성 create()
    //TODO Method : POST, URL : "/schedules"
    //TODO RequestBody CreateScheduleRequest (username, title, content)
    //TODO ResponseEntity<CreateScheduleResponse> (id, username, title, content, createdAt, modifiedAt), CREATED

    //TODO 일정 조회 다건 getAll()
    //TODO Method : GET,  URL : "/schedules"
    //TODO RequestParam (required = false) (String username)
    //TODO ResponseEntity<List<GetScheduleResponse>> (id, username, title, content, createdAt, modifiedAt), OK

    //TODO 일정 조회 단건 getOne()
    //TODO Method : GET,  URL : "/schedules/{id}"
    //TODO PathVariable (String username)
    //TODO ResponseEntity<GetOneScheduleResponse> (id, username, title, content, createdAt, modifiedAt), OK

    //TODO 일정 수정 update()
    //TODO Method : PUT,  URL : "/schedules/{id}"
    //TODO PathVariable (String username)
    //TODO RequestBody UpdateScheduleRequest (title, content)
    //TODO ResponseEntity<UpdateScheduleResponse> (id, username, title, content, createdAt, modifiedAt), OK

    //TODO 일정 삭제 delete()
    //TODO Method : DELETE,  URL : "/schedules/{id}"
    //TODO PathVariable (String username)
    //TODO ResponseEntity<Void> NO_CONTENT
}
