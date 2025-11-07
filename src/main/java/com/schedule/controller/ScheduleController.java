package com.schedule.controller;

import com.schedule.dto.CreateScheduleRequest;
import com.schedule.dto.CreateScheduleResponse;
import com.schedule.dto.GetScheduleResponse;
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

    /**
     * 일정 조회 다건
     * @param username 작성 유저명
     * @return ResponseEntity<List<GetScheduleResponse>> (id, username, title, content, createdAt, modifiedAt)
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll(@RequestParam(required = false) String username) {

        List<GetScheduleResponse> resultList = scheduleService.getAll(username);

        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    /**
     * 일정 조회 단건
     * @param id 일정 고유 ID
     * @return ResponseEntity<GetOneScheduleResponse> (id, username, title, content, createdAt, modifiedAt)
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetScheduleResponse> getOne(@PathVariable Long id) {

        GetScheduleResponse result = scheduleService.getOne(id);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //TODO 일정 수정 update()
    //TODO Method : PUT,  URL : "/schedules/{id}"
    //TODO PathVariable (Long id)
    //TODO RequestBody UpdateScheduleRequest (title, content)
    //TODO ResponseEntity<UpdateScheduleResponse> (id, username, title, content, createdAt, modifiedAt), OK

    //TODO 일정 삭제 delete()
    //TODO Method : DELETE,  URL : "/schedules/{id}"
    //TODO PathVariable (Long id)
    //TODO ResponseEntity<Void> NO_CONTENT
}
