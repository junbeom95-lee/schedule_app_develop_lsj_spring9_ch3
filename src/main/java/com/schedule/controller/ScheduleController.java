package com.schedule.controller;

import com.schedule.dto.CreateScheduleRequest;
import com.schedule.dto.CreateScheduleResponse;
import com.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * @param request CreateScheduleRequest (username, title, content)
     * @return ResponseEntity<CreateScheduleResponse> (id, username, title, content, createdAt, modifiedAt)
     */
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(@RequestBody CreateScheduleRequest request) {

        CreateScheduleResponse result = scheduleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

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
